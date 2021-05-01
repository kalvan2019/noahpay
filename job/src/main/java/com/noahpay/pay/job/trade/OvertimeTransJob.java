package com.noahpay.pay.job.trade;

import com.noahpay.pay.commons.db.trade.mapper.AsyncEventHandleMapper;
import com.noahpay.pay.commons.db.trade.model.AsyncEventHandle;
import com.noahpay.pay.job.trade.cmd.QuickTransSynQueryCmd;
import com.noahpay.pay.job.trade.util.TransThreadMonitor;
import com.noahpay.pay.trade.iface.IJob;
import com.kalvan.web.thread.BaseAsynchronousCmd;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import javax.annotation.Resource;
import java.util.List;

/**
 * 同步渠道状态
 *
 * @author chenliang
 */
@Slf4j
//@ElasticJobConf(cron = "*/5 * * * * ?", shardingItemParameters = "0=0", overwrite = true, description = "同步渠道状态")
public class OvertimeTransJob extends BaseAsynchronousCmd implements SimpleJob {
    /**
     * 最小线程数
     */
    private static final int MIN_POOL_SIZE = 30;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 100;
    /**
     * 每次查询出总记录数
     */
    private static final int QUERY_ROW = 200;
    @Resource
    IJob jobQuick;

    @Resource
    AsyncEventHandleMapper asyncEventHandleMapper;

    OvertimeTransJob() {
        super(30, 30, 20, 100);
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
            list = asyncEventHandleMapper.queryOvertime(QUERY_ROW);
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
                if (TransThreadMonitor.isRun(asyncEventHandle.getTransId())) {
                    continue;
                }
                TransThreadMonitor.addWorker(asyncEventHandle.getTransId());
                threadPool.execute(new QuickTransSynQueryCmd(asyncEventHandle, asyncEventHandleMapper, jobQuick));
            }
        } catch (Throwable e) {
            log.error("异步任务处理异常", e);
            TransThreadMonitor.clear();
        }
        log.info("异步任务完成,工作线程{},等待线程{}", threadPool.getActiveCount(), threadPool.getQueue().size());
    }
}
