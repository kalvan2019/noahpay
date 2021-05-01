package com.noahpay.pay.commons.db.cust.mapper;

import com.noahpay.pay.commons.db.cust.model.FeeRule;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 计费规则配置Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface FeeRuleMapper extends IBaseMapper<FeeRule> {

    /**
     * 获取计费规则
     *
     * @param feeRule feeRule
     * @return FeeRule
     */
    List<FeeRule> getFeeRule(@Param("feeRule") String feeRule);
}
