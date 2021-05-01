package com.noahpay.pay.fee.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.cust.mapper.FeeMerchantMapper;
import com.noahpay.pay.commons.db.cust.mapper.FeeRuleMapper;
import com.noahpay.pay.commons.db.cust.mapper.FeeSegmentRuleMapper;
import com.noahpay.pay.commons.db.cust.model.FeeMerchant;
import com.noahpay.pay.commons.db.cust.model.FeeRule;
import com.noahpay.pay.commons.db.cust.model.FeeSegmentRule;
import com.kalvan.enums.cache.CacheTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Slf4j
@Service
public class CacheFeeService {
    @Resource
    FeeMerchantMapper feeMerchantMapper;
    @Resource
    FeeRuleMapper feeRuleMapper;
    @Resource
    FeeSegmentRuleMapper feeSegmentRuleMapper;

    @Cached(name = CacheTypeEnum.FEE_MERCHANT, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public FeeMerchant getFeeMerchant(Long merchantNo, Integer transType) {
        return feeMerchantMapper.getFeeMerchant(merchantNo, transType);
    }

    @CacheInvalidate(name = CacheTypeEnum.FEE_MERCHANT)
    public void deleteFeeMerchant(Long merchantNo, Integer transType) {
        log.info("删除缓存{}-{}", merchantNo, transType);
    }

    @Cached(name = CacheTypeEnum.FEE_RULE, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public List<FeeRule> getFeeRule(String feeRule) throws BizException {
        return feeRuleMapper.getFeeRule(feeRule);
    }

    @CacheInvalidate(name = CacheTypeEnum.FEE_RULE)
    public void deleteFeeRule(String feeRule) {
        log.info("删除缓存{}", feeRule);
    }

    @Cached(name = CacheTypeEnum.FEE_SEGMENT_RULE, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public List<FeeSegmentRule> getFeeSegmentRule(String feeSegmentRule) throws BizException {
        return feeSegmentRuleMapper.getFeeSegmentRule(feeSegmentRule);
    }

    @CacheInvalidate(name = CacheTypeEnum.FEE_SEGMENT_RULE)
    public void deleteFeeSegmentRule(String feeSegmentRule) {
        log.info("删除缓存{}", feeSegmentRule);
    }
}
