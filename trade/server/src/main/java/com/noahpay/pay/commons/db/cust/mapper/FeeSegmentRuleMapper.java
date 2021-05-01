package com.noahpay.pay.commons.db.cust.mapper;

import com.noahpay.pay.commons.db.cust.model.FeeSegmentRule;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分段计费规则配置Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface FeeSegmentRuleMapper extends IBaseMapper<FeeSegmentRule> {
    /**
     * 获取分段计费配置
     *
     * @param feeSegmentRule feeSegmentRule
     * @return FeeSegmentRule
     */
    List<FeeSegmentRule> getFeeSegmentRule(@Param("feeSegmentRule") String feeSegmentRule);
}
