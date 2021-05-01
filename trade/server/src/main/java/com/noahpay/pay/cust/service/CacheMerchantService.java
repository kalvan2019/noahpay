package com.noahpay.pay.cust.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.noahpay.pay.commons.db.cust.mapper.MerchantInfoMapper;
import com.noahpay.pay.commons.db.cust.mapper.MerchantProductTransMapper;
import com.noahpay.pay.commons.db.cust.model.MerchantInfo;
import com.noahpay.pay.commons.db.cust.model.MerchantProductTrans;
import com.kalvan.enums.cache.CacheTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Slf4j
@Service
public class CacheMerchantService {
    @Resource
    MerchantInfoMapper merchantInfoMapper;
    @Resource
    MerchantProductTransMapper merchantProductTransMapper;

    @Cached(name = CacheTypeEnum.MERCHANT, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public MerchantInfo getMerchant(Long merchantNo) {
        return merchantInfoMapper.selectByUk(merchantNo);
    }

    @CacheInvalidate(name = CacheTypeEnum.MERCHANT)
    public void deleteMerchant(Long merchantNo) {
        log.info("删除缓存{}", merchantNo);
    }

    @Cached(name = CacheTypeEnum.MERCHANT_PRODUCT_TRANS, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public MerchantProductTrans getMerchantProductTrans(Long merchantNo, Integer transType) {
        MerchantProductTrans uk = new MerchantProductTrans();
        uk.setMerchantNo(merchantNo);
        uk.setTransType(transType);
        return merchantProductTransMapper.selectByUk(uk);
    }

    @CacheInvalidate(name = CacheTypeEnum.MERCHANT_PRODUCT_TRANS)
    public void deleteMerchantProductTrans(Long merchantNo, Integer transType) {
        log.info("删除缓存{}-{}", merchantNo, transType);
    }
}
