package com.noahpay.pay.commons.db.risk.mapper;

import com.kalvan.db.mybatis.IBaseMapper;
import com.noahpay.pay.commons.db.risk.model.ConsumerTransSum;
import org.springframework.stereotype.Repository;

/**
 * 客户交易额度累计Mapper
 *
 * @author kalvan.tools:kalvan
 */
@Repository
public interface ConsumerTransSumMapper extends IBaseMapper<ConsumerTransSum> {

}
