package com.noahpay.pay.job.trade;

import com.noahpay.pay.commons.db.trade.mapper.AsyncEventHandleMapper;
import com.noahpay.pay.commons.db.trade.model.AsyncEventHandle;
import com.noahpay.pay.job.trade.cmd.AccountOutUndoCmd;
import com.noahpay.pay.job.trade.util.AccountThreadMonitor;
import com.noahpay.pay.job.trade.util.TransThreadMonitor;
import com.noahpay.pay.trade.constant.EventTypeEnum;
import com.noahpay.pay.trade.iface.IJob;
import com.kalvan.web.thread.BaseAsynchronousCmd;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import javax.annotation.Resource;
import java.util.List;

/**
 * 异步冲正
 *
 * @author chenliang
 */
@Slf4j
//@ElasticJobConf(cron = "*/1 * * * * ?", shardingItemParameters = "0=0", overwrite = true, description = "异步冲正")
public class AccountOutUndoJob extends BaseAsynchronousCmd implements SimpleJob {
    /**
     * 最小线程数
     */
    private static final int MIN_POOL_SIZE = 10;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 100;
    /**
     * 每次查询出总记录数
     */
    private static final int QUERY_ROW = 100;
    /**
     * 按accountNo分组,每组取前多少条
     */
    private static final int GROUP_ROW = 5;
    @Resource
    IJob iJob;
    @Resource
    AsyncEventHandleMapper asyncEventHandleMapper;

    AccountOutUndoJob() {
        super(10, 30, 20, 100);
    }

    @Override
    public void execute(ShardingContext shardingContext) {
        String parameter = shardingContext.getJobParameter();
        if (StringUtils.isNotBlank(parameter)) {
            int bf = Integer.parseInt(parameter);
            if (bf >= MIN_POOL_SIZE && bf <= MAX_POOL_SIZE) {
                //手动设置线程池并发
                threadPool.setCorePoolSize(bf);
                threadPool.setMaximumPoolSize(bf);
            }
        }
        if (isOutOfRange()) {
            log.warn("线程溢出,工作线程{},等待线程{}", threadPool.getActiveCount(), threadPool.getQueue().size());
            return;
        }
        //获取db中的数据
        List<AsyncEventHandle> list = null;
        try {
            list = asyncEventHandleMapper.queryAccountOutUndo(EventTypeEnum.ACCOUNT_UNDO.code, QUERY_ROW, GROUP_ROW);
            log.info("异步任务{},工作线程{},等待线程{}", list.size(), threadPool.getActiveCount(), threadPool.getQueue().size());
        } catch (Exception e) {
            log.error("查询异步事件表异常", e);
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        try {
            for (AsyncEventHandle asyncEventHandle : list) {
                if (isOutOfRange()) {
                    log.warn("线程溢出,工作线程{},等待线程{}", threadPool.getActiveCount(), threadPool.getQueue().size());
                    Thread.sleep(500);
                    continue;
                }
                Long accountNo = asyncEventHandle.getAccountNo();
                // 单账户并发控制
                int count = AccountThreadMonitor.getConcurrent(accountNo);
                if (count >= AccountThreadMonitor.CONCURRENT) {
                    log.info("{}当前并发量:{}超过设置{}", accountNo, count, AccountThreadMonitor.CONCURRENT);
                    continue;
                }
                AccountThreadMonitor.addWorker(accountNo);
                TransThreadMonitor.addWorker(asyncEventHandle.getTransId());
                threadPool.execute(new AccountOutUndoCmd(asyncEventHandle, asyncEventHandleMapper, iJob));
            }
        } catch (Exception e) {
            log.error("异步任务处理异常", e);
            AccountThreadMonitor.clear();
            TransThreadMonitor.clear();
        }
        log.info("异步任务完成,工作线程{},等待线程{}", threadPool.getActiveCount(), threadPool.getQueue().size());
    }
}
