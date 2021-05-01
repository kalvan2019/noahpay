package com.noahpay.pay.fee.event;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kalvan.enums.cache.CacheTypeEnum;
import com.kalvan.web.event.CacheRefreshEvent;
import com.noahpay.pay.cust.service.CacheMerchantService;
import com.noahpay.pay.fee.service.CacheFeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 异步更新缓存
 *
 * @author chenliang
 */
@Component
@Slf4j
public class FeeCacheRefreshHandler {
    @Resource
    private CacheFeeService cacheFeeService;

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.FEE_MERCHANT + "')")
    public void feeMerchant(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        if (JSONUtil.isJsonObj(event.getCacheKey())) {
            JSONObject jsonObject = JSON.parseObject(event.getCacheKey());
            cacheFeeService.deleteFeeMerchant((Long) jsonObject.get("merchantNo"), (Integer) jsonObject.get("transType"));
        }
    }

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.FEE_RULE + "')")
    public void feeRule(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        cacheFeeService.deleteFeeRule(event.getCacheKey());
    }

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.FEE_SEGMENT_RULE + "')")
    public void feeSegmentRule(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        cacheFeeService.deleteFeeSegmentRule(event.getCacheKey());
    }
}