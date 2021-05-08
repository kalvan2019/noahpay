package com.noahpay.pay.risk.event;

import com.noahpay.pay.enums.cache.CacheTypeEnum;
import com.noahpay.pay.risk.service.CacheRiskService;
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
public class RiskCacheRefreshHandler {
    @Resource
    private CacheRiskService cacheRiskService;

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.BLACK_LIST + "')")
    public void blackList(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        cacheRiskService.deleteBlackList();
    }
}