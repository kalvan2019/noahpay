package com.noahpay.pay.risk.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.kalvan.client.constant.SwitchEnum;
import com.noahpay.pay.commons.db.risk.mapper.BlackListMapper;
import com.noahpay.pay.commons.db.risk.mapper.MerchantTransSumMapper;
import com.noahpay.pay.commons.db.risk.model.BlackList;
import com.noahpay.pay.commons.db.risk.model.MerchantTransSum;
import com.noahpay.pay.enums.cache.CacheTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Service
@Slf4j
public class CacheRiskService {

    @Resource
    MerchantTransSumMapper merchantTransSumMapper;
    @Resource
    BlackListMapper blackListMapper;

    /**
     * 缓存获取商户交易累计
     */
    @Cached(name = CacheTypeEnum.BLACK_LIST, cacheType = CacheType.LOCAL, expire = 5, localLimit = 10000, cacheNullValue = true)
    @CacheRefresh(refresh = 5)
    public MerchantTransSum getMerchantTradeSum(Long merchantNo, Integer transType) {
        MerchantTransSum uk = new MerchantTransSum();
        uk.setMerchantNo(merchantNo);
        uk.setTransType(transType);
        return merchantTransSumMapper.selectByUk(uk);
    }

    /**
     * 缓存获取黑名单
     */
    @Cached(name = CacheTypeEnum.BLACK_LIST, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public List<BlackList> getBlackList() {
        BlackList query = new BlackList();
        query.setState(SwitchEnum.OPEN.code);
        return blackListMapper.select(query);
    }

    /**
     * 删除缓存
     */
    @CacheInvalidate(name = CacheTypeEnum.BLACK_LIST)
    public void deleteBlackList() {
        log.info("删除缓存");
    }
}
