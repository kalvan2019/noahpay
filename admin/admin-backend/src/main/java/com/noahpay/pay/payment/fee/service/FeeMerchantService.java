package com.noahpay.pay.payment.fee.service;

import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.FeeMerchantMapper;
import com.noahpay.pay.commons.db.cust.model.FeeMerchant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商户计费配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("feeMerchantService")
public class FeeMerchantService extends BaseAuditService<FeeMerchant> {
    @Resource
    FeeMerchantMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(FeeMerchant feeMerchant) {
        FeeMerchant feeMerchantQuery = new FeeMerchant();
        feeMerchantQuery.setMerchantNo(feeMerchant.getMerchantNo());
        feeMerchantQuery.setTransType(feeMerchant.getTransType());
        if (mapper.selectCount(feeMerchantQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        feeMerchant.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(feeMerchant);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(FeeMerchant feeMerchant) {
        FeeMerchant feeMerchantDb = mapper.selectByUk(feeMerchant.getId());
        if (feeMerchantDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用feeMerchant
        FeeMerchant feeMerchantNew = new FeeMerchant();
        feeMerchantNew.setId(feeMerchant.getId());
        feeMerchantNew.setMerchantNo(feeMerchant.getMerchantNo());
        feeMerchantNew.setTransType(feeMerchant.getTransType());
        feeMerchantNew.setFeeMode(feeMerchant.getFeeMode());
        feeMerchantNew.setFeeObject(feeMerchant.getFeeObject());
        feeMerchantNew.setFeeRule(feeMerchant.getFeeRule());
        feeMerchantNew.setState(feeMerchant.getState());
        return this.editDataAudit(feeMerchantDb, feeMerchantNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            FeeMerchant feeMerchantDb = mapper.selectByUk(id);
            if (feeMerchantDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(feeMerchantDb);
        }
        return resultNum;
    }
}
