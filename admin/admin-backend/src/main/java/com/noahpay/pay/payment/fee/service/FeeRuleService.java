package com.noahpay.pay.payment.fee.service;

import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.FeeRuleMapper;
import com.noahpay.pay.commons.db.cust.model.FeeRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 计费规则配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("feeRuleService")
public class FeeRuleService extends BaseAuditService<FeeRule> {
    @Resource
    FeeRuleMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(FeeRule feeRule) {
        FeeRule feeRuleQuery = new FeeRule();
        feeRuleQuery.setFeeRule(feeRule.getFeeRule());
        feeRuleQuery.setChannelNo(feeRule.getChannelNo());
        feeRuleQuery.setPayType(feeRule.getPayType());
        feeRuleQuery.setBankType(feeRule.getBankType());
        feeRuleQuery.setBankAccountType(feeRule.getBankAccountType());
        feeRuleQuery.setFeeType(feeRule.getFeeType());
        if (mapper.selectCount(feeRuleQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        feeRule.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(feeRule);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(FeeRule feeRule) {
        FeeRule feeRuleDb = mapper.selectByUk(feeRule.getId());
        if (feeRuleDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用feeRule
        FeeRule feeRuleNew = new FeeRule();
        feeRuleNew.setId(feeRule.getId());
        feeRuleNew.setFeeRule(feeRule.getFeeRule());
        feeRuleNew.setChannelNo(feeRule.getChannelNo());
        feeRuleNew.setPayType(feeRule.getPayType());
        feeRuleNew.setBankType(feeRule.getBankType());
        feeRuleNew.setBankAccountType(feeRule.getBankAccountType());
        feeRuleNew.setFeeType(feeRule.getFeeType());
        feeRuleNew.setFeeRate(feeRule.getFeeRate());
        feeRuleNew.setFixFee(feeRule.getFixFee());
        feeRuleNew.setMinFee(feeRule.getMinFee());
        feeRuleNew.setFeeSegmentRule(feeRule.getFeeSegmentRule());
        feeRuleNew.setState(feeRule.getState());
        return this.editDataAudit(feeRuleDb, feeRuleNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            FeeRule feeRuleDb = mapper.selectByUk(id);
            if (feeRuleDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(feeRuleDb);
        }
        return resultNum;
    }
}
