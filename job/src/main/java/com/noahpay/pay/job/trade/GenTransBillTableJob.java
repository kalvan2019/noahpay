package com.noahpay.pay.job.trade;

import com.noahpay.pay.commons.db.trade.mapper.TransBillMapper;
import com.noahpay.pay.commons.db.trade.mapper.TransPayBillMapper;
import com.kalvan.job.annotation.ElasticJobConf;
import com.kalvan.web.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import javax.annotation.Resource;

/**
 * 生成交易流水表分表
 *
 * @author chenliang
 */
@Slf4j
@ElasticJobConf(cron = "0/30 * * * * ?", shardingItemParameters = "0=0", overwrite = true, description = "生成交易流水表分表")
public class GenTransBillTableJob implements SimpleJob {
    public static final int TABLE_SIZE = 10;
    @Resource
    TransBillMapper transBillMapper;
    @Resource
    TransPayBillMapper transPayBillMapper;

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("生成交易流水表分表");
        for (int i = -1; i <= TABLE_SIZE; i++) {
            String day = DateUtil.getDateString(DateUtil.offsetDay(DateUtil.date(), i));
            transBillMapper.createBillByDate(day);
            transPayBillMapper.createBillByDate(day);
        }
        log.info("生成交易流水表分表完成");
    }
}
