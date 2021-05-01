package com.noahpay.pay.job.trade;

import com.noahpay.pay.commons.db.trade.mapper.AsyncEventHandleMapper;
import com.kalvan.job.annotation.ElasticJobConf;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import javax.annotation.Resource;

/**
 * 日终清理已完结的异步事件记录
 *
 * @author chenliang
 */
@Slf4j
@ElasticJobConf(cron = "0 50 23 * * ?", shardingItemParameters = "0=0", overwrite = true, description = "日终清理已完结的异步事件记录")
public class ClearAsyncEventHandleJob implements SimpleJob {
    @Resource
    AsyncEventHandleMapper asyncEventHandleMapper;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("清理AsyncEventHandle");
        try {
            while (true) {
                int row = asyncEventHandleMapper.cleanHistory();
                log.info("清理数据{}行", row);
                if (row == 0) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("清理AsyncEventHandle异常", e);
        }
        log.info("清理AsyncEventHandle完成");
    }
}
