package com.noahpay.pay.commons.db.risk.mapper;

import com.noahpay.pay.commons.db.risk.model.MerchantTransSum;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 商户交易额度累计Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface MerchantTransSumMapper extends IBaseMapper<MerchantTransSum> {
    int updateTransSum(@Param("merchantNo") Long merchantNo, @Param("transType") Integer transType, @Param("amount") Long amount, @Param("number") Long number);
}
