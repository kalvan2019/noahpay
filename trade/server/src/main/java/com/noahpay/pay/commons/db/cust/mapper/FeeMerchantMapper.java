package com.noahpay.pay.commons.db.cust.mapper;

import com.noahpay.pay.commons.db.cust.model.FeeMerchant;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商户计费配置Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface FeeMerchantMapper extends IBaseMapper<FeeMerchant> {

    /**
     * 获取计费配置
     *
     * @param merchantNo merchantNo
     * @param transType  transType
     * @return FeeMerchant
     */
    FeeMerchant getFeeMerchant(@Param("merchantNo") Long merchantNo, @Param("transType") Integer transType);
}
