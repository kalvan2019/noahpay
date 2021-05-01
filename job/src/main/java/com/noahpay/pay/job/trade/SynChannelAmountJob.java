package com.noahpay.pay.job.trade;

import com.noahpay.pay.channel.iface.IChannelMerchant;
import com.kalvan.client.model.Request;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import javax.annotation.Resource;

/**
 * 同步渠道余额
 *
 * @author chenliang
 */
@Slf4j
//@ElasticJobConf(cron = "0/10 * * * * ?", shardingItemParameters = "0=0", overwrite = true, description = "同步渠道余额")
public class SynChannelAmountJob implements SimpleJob {
    @Resource
    IChannelMerchant iChannelMerchant;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("同步渠道余额");
        iChannelMerchant.merchantBalance(new Request());
    }
}
