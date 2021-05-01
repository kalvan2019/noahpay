package com.noahpay.pay.payment.fee.service;

import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.FeeSegmentRuleMapper;
import com.noahpay.pay.commons.db.cust.model.FeeSegmentRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 分段计费规则配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("feeSegmentRuleService")
public class FeeSegmentRuleService extends BaseAuditService<FeeSegmentRule> {
    @Resource
    FeeSegmentRuleMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(FeeSegmentRule feeSegmentRule) {
        feeSegmentRule.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(feeSegmentRule);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(FeeSegmentRule feeSegmentRule) {
        FeeSegmentRule feeSegmentRuleDb = mapper.selectByUk(feeSegmentRule.getId());
        if (feeSegmentRuleDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用feeSegmentRule
        FeeSegmentRule feeSegmentRuleNew = new FeeSegmentRule();
        feeSegmentRuleNew.setId(feeSegmentRule.getId());
        feeSegmentRuleNew.setFeeSegmentRule(feeSegmentRule.getFeeSegmentRule());
        feeSegmentRuleNew.setBeginAmount(feeSegmentRule.getBeginAmount());
        feeSegmentRuleNew.setEndAmount(feeSegmentRule.getEndAmount());
        feeSegmentRuleNew.setFeeType(feeSegmentRule.getFeeType());
        feeSegmentRuleNew.setFeeRate(feeSegmentRule.getFeeRate());
        feeSegmentRuleNew.setFixFee(feeSegmentRule.getFixFee());
        feeSegmentRuleNew.setState(feeSegmentRule.getState());
        return this.editDataAudit(feeSegmentRuleDb, feeSegmentRuleNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            FeeSegmentRule feeSegmentRuleDb = mapper.selectByUk(id);
            if (feeSegmentRuleDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(feeSegmentRuleDb);
        }
        return resultNum;
    }
}
