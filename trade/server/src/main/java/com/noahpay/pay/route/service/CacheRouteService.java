package com.noahpay.pay.route.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.kalvan.client.constant.SwitchEnum;
import com.noahpay.pay.commons.db.channel.mapper.ChannelSupportBankMapper;
import com.noahpay.pay.commons.db.channel.mapper.ChannelSupportPayTypeMapper;
import com.noahpay.pay.commons.db.channel.mapper.RouteMerchantMapper;
import com.noahpay.pay.commons.db.channel.mapper.RouteRuleMapper;
import com.noahpay.pay.commons.db.channel.model.*;
import com.kalvan.enums.cache.CacheTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Service
@Slf4j
public class CacheRouteService {
    @Resource
    private RouteMerchantMapper routeMerchantMapper;
    @Resource
    private RouteRuleMapper routeRuleMapper;
    @Resource
    private ChannelSupportPayTypeMapper channelSupportPayTypeMapper;
    @Resource
    private ChannelSupportBankMapper channelSupportBankMapper;
    @Resource
    private CacheChannelService cacheChannelService;

    @Cached(name = CacheTypeEnum.ROUTE_MERCHANT, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public RouteMerchant getRouteMerchant(Long merchantNo, String payType) {
        RouteMerchant uk = new RouteMerchant();
        uk.setMerchantNo(merchantNo);
        uk.setPayType(payType);
        RouteMerchant routeMerchant = routeMerchantMapper.selectByUk(uk);
        if (routeMerchant == null || SwitchEnum.OPEN.code != routeMerchant.getState()) {
            return null;
        }
        return routeMerchant;
    }

    @CacheInvalidate(name = CacheTypeEnum.ROUTE_MERCHANT)
    public void deleteRouteMerchant(Long merchantNo, String payType) {
        log.info("删除缓存{}-{}", merchantNo, payType);
    }

    @Cached(name = CacheTypeEnum.ROUTE_RULE, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public List<RouteRule> getRouteRule(String routeRule, Integer bankAccountType) {
        RouteRule query = new RouteRule();
        query.setRouteRule(routeRule);
        query.setState(SwitchEnum.OPEN.code);
        List<RouteRule> routeRules = routeRuleMapper.select(query);
        routeRules = routeRules.stream().filter(temp -> {
            ChannelInfo channelInfo = cacheChannelService.getChannelInfo(temp.getChannelNo());
            if (channelInfo == null) {
                return false;
            }
            if (SwitchEnum.OPEN.code != channelInfo.getState()) {
                return false;
            }
            return bankAccountType == null || temp.getBankAccountType() == 0 || temp.getBankAccountType().equals(bankAccountType);
        }).collect(Collectors.toList());
        return routeRules;
    }

    @CacheInvalidate(name = CacheTypeEnum.ROUTE_RULE)
    public void deleteRouteRule(String routeRule, Integer bankAccountType) {
        log.info("删除缓存{}-{}", routeRule, bankAccountType);
    }


    @Cached(name = CacheTypeEnum.CHANNEL_SUPPORT_PAY_TYPE, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public ChannelSupportPayType getChannelSupportPayType(Integer channelNo, String payType) {
        ChannelSupportPayType uk = new ChannelSupportPayType();
        uk.setChannelNo(channelNo);
        uk.setPayType(payType);
        ChannelSupportPayType channelSupportPayType = channelSupportPayTypeMapper.selectByUk(uk);
        if (channelSupportPayType == null || SwitchEnum.OPEN.code != channelSupportPayType.getState()) {
            return null;
        }
        return channelSupportPayType;
    }

    @CacheInvalidate(name = CacheTypeEnum.CHANNEL_SUPPORT_PAY_TYPE)
    public void deleteChannelSupportPayType(Integer channelNo, String payType) {
        log.info("删除缓存{}-{}", channelNo, payType);
    }

    @Cached(name = CacheTypeEnum.CHANNEL_SUPPORT_BANK, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public List<ChannelSupportBank> getChannelSupportBanks(Integer channelNo, String payType) {
        return channelSupportBankMapper.querySupportBank(channelNo, payType);
    }

    @CacheInvalidate(name = CacheTypeEnum.CHANNEL_SUPPORT_BANK)
    public void deleteChannelSupportBanks(Integer channelNo, String payType) {
        log.info("删除缓存{}-{}", channelNo, payType);
    }
}
