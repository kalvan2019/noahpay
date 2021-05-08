package com.noahpay.pay.route.event;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.noahpay.pay.enums.cache.CacheTypeEnum;
import com.kalvan.web.event.CacheRefreshEvent;
import com.noahpay.pay.route.service.CacheChannelService;
import com.noahpay.pay.route.service.CacheRouteService;
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
public class RouteCacheRefreshHandler {
    @Resource
    private CacheChannelService cacheChannelService;
    @Resource
    private CacheRouteService cacheRouteService;

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.CHANNEL_EXT_PARAM + "')")
    public void channelExtParam(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        cacheChannelService.deleteChannelExtParam(Integer.parseInt(event.getCacheKey()));
    }

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.CHANNEL_INFO + "')")
    public void channelInfo(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        cacheChannelService.deleteChannelInfo(Integer.parseInt(event.getCacheKey()));
    }

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.CHANNEL_SUPPORT_BANK + "')")
    public void channelSupportBank(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        if (JSONUtil.isJsonObj(event.getCacheKey())) {
            JSONObject jsonObject = JSON.parseObject(event.getCacheKey());
            cacheRouteService.deleteChannelSupportBanks((Integer) jsonObject.get("channelNo"), String.valueOf(jsonObject.get("payType")));
        }
    }

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.CHANNEL_SUPPORT_PAY_TYPE + "')")
    public void channelSupportPayType(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        if (JSONUtil.isJsonObj(event.getCacheKey())) {
            JSONObject jsonObject = JSON.parseObject(event.getCacheKey());
            cacheRouteService.deleteChannelSupportPayType((Integer) jsonObject.get("channelNo"), String.valueOf(jsonObject.get("payType")));
        }
    }

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.ROUTE_MERCHANT + "')")
    public void routeMerchant(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        if (JSONUtil.isJsonObj(event.getCacheKey())) {
            JSONObject jsonObject = JSON.parseObject(event.getCacheKey());
            cacheRouteService.deleteRouteMerchant((Long) jsonObject.get("merchantNo"), String.valueOf(jsonObject.get("payType")));
        }
    }

    @Async("lazyTraceExecutor")
    @EventListener(condition = "#event.cacheType.equals('" + CacheTypeEnum.ROUTE_RULE + "')")
    public void routeRule(CacheRefreshEvent event) {
        log.info("开始刷新缓存{}", event.getCacheType());
        if (JSONUtil.isJsonObj(event.getCacheKey())) {
            JSONObject jsonObject = JSON.parseObject(event.getCacheKey());
            cacheRouteService.deleteRouteRule(String.valueOf(jsonObject.get("routeRule")), (Integer) jsonObject.get("bankAccountType"));
        }
    }
}