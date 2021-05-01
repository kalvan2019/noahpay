package com.noahpay.pay.commons.db.risk.mapper;

import com.kalvan.db.mybatis.IBaseMapper;
import com.noahpay.pay.commons.db.risk.model.MerchantTransSum;
import org.springframework.stereotype.Repository;

/**
 * 商户交易额度累计Mapper
 *
 * @author kalvan.tools:kalvan
 */
@Repository
public interface MerchantTransSumMapper extends IBaseMapper<MerchantTransSum> {

}
