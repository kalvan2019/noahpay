package com.noahpay.pay.payment.cust.service;

import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.PartnerRelationInfoMapper;
import com.noahpay.pay.commons.db.cust.model.PartnerRelationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 合作方关系维护表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("partnerRelationInfoService")
public class PartnerRelationInfoService extends BaseAuditService<PartnerRelationInfo> {
    @Resource
    PartnerRelationInfoMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(PartnerRelationInfo partnerRelationInfo) {
        PartnerRelationInfo partnerRelationInfoQuery = new PartnerRelationInfo();
        partnerRelationInfoQuery.setMerchantNo(partnerRelationInfo.getMerchantNo());
        if (mapper.selectCount(partnerRelationInfoQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        partnerRelationInfo.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(partnerRelationInfo);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(PartnerRelationInfo partnerRelationInfo) {
        PartnerRelationInfo partnerRelationInfoDb = mapper.selectByUk(partnerRelationInfo.getId());
        if (partnerRelationInfoDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        PartnerRelationInfo partnerRelationInfoNew = new PartnerRelationInfo();
        partnerRelationInfoNew.setId(partnerRelationInfo.getId());
        partnerRelationInfoNew.setPartnerNo(partnerRelationInfo.getPartnerNo());
        partnerRelationInfoNew.setMerchantNo(partnerRelationInfo.getMerchantNo());
        partnerRelationInfoNew.setFeeRate(partnerRelationInfo.getFeeRate());
        partnerRelationInfoNew.setWithdrawFee(partnerRelationInfo.getWithdrawFee());
        partnerRelationInfoNew.setState(partnerRelationInfo.getState());
        return this.editDataAudit(partnerRelationInfoDb, partnerRelationInfoNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            PartnerRelationInfo partnerRelationInfoDb = mapper.selectByUk(id);
            if (partnerRelationInfoDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(partnerRelationInfoDb);
        }
        return resultNum;
    }
}
