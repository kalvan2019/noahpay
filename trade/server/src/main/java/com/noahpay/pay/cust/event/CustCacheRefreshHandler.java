package com.noahpay.pay.cust.event;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.noahpay.pay.cust.service.CacheMerchantService;
import com.noahpay.pay.enums.cache.CacheTypeEnum;
import com.kalvan.web.event.CacheRefreshEvent;
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
public class CustCacheRefreshHandler {

    @Resource
    private CacheMerchantService cacheMerchantService;

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.MERCHANT + "')")
    public void merchant(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        cacheMerchantService.deleteMerchant(Long.parseLong(event.getCacheKey()));
    }

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.MERCHANT_PRODUCT_TRANS + "')")
    public void merchantProductTrans(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        if (JSONUtil.isJsonObj(event.getCacheKey())) {
            JSONObject jsonObject = JSON.parseObject(event.getCacheKey());
            cacheMerchantService.deleteMerchantProductTrans((Long) jsonObject.get("merchantNo"), (Integer) jsonObject.get("transType"));
        }
    }
}