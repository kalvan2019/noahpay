package com.noahpay.pay.job.trade;

import com.noahpay.pay.commons.db.trade.mapper.AsyncTransHandleMapper;
import com.kalvan.job.annotation.ElasticJobConf;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import javax.annotation.Resource;

/**
 * 日终清理已完结的异步交易记录
 *
 * @author chenliang
 */
@Slf4j
@ElasticJobConf(cron = "0 55 23 * * ?", shardingItemParameters = "0=0", overwrite = true, description = "日终清理已完结的异步交易记录")
public class ClearAsyncTransHandleJob implements SimpleJob {
    @Resource
    AsyncTransHandleMapper asyncTransHandleMapper;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("清理AsyncTransHandle");
        try {
            while (true) {
                int row = asyncTransHandleMapper.cleanHistory();
                log.info("清理数据{}行", row);
                if (row == 0) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("清理AsyncTransHandle异常", e);
        }
        log.info("清理AsyncTransHandle完成");
    }
}
