package com.noahpay.pay.job.trade;

import com.kalvan.client.model.Request;
import com.kalvan.job.annotation.ElasticJobConf;
import com.noahpay.pay.route.iface.IRouteDataHandle;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import javax.annotation.Resource;

/**
 * 日终重置渠道日额度
 *
 * @author chenliang
 */
@Slf4j
@ElasticJobConf(cron = "0 40 23 * * ?", shardingItemParameters = "0=0", overwrite = true, description = "日终重置渠道日额度")
public class ResetChannelUseJob implements SimpleJob {
    @Resource
    IRouteDataHandle iChannelDataHandle;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("重置渠道使用额度");
        iChannelDataHandle.resetAdvanceUseAmount(new Request());
        iChannelDataHandle.resetChannelUse(new Request());
    }
}
