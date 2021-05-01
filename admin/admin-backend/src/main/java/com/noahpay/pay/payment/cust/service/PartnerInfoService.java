package com.noahpay.pay.payment.cust.service;

import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.PartnerInfoMapper;
import com.noahpay.pay.commons.db.cust.model.PartnerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 合作方信息表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("partnerInfoService")
public class PartnerInfoService extends BaseAuditService<PartnerInfo> {
    @Resource
    PartnerInfoMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(PartnerInfo partnerInfo) {
        //TODO 调用客户系统生成合作方号
        partnerInfo.setPartnerNo(1000L);
        partnerInfo.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(partnerInfo);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(PartnerInfo partnerInfo) {
        PartnerInfo partnerInfoDb = mapper.selectByUk(partnerInfo.getId());
        if (partnerInfoDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用partnerInfo
        PartnerInfo partnerInfoNew = new PartnerInfo();
        partnerInfoNew.setId(partnerInfo.getId());
        partnerInfoNew.setPartnerNo(partnerInfo.getPartnerNo());
        partnerInfoNew.setPartnerName(partnerInfo.getPartnerName());
        partnerInfoNew.setCustNo(partnerInfo.getCustNo());
        partnerInfoNew.setBusinessLicenseNo(partnerInfo.getBusinessLicenseNo());
        partnerInfoNew.setBankAccountNo(partnerInfo.getBankAccountNo());
        partnerInfoNew.setBankAccountName(partnerInfo.getBankAccountName());
        partnerInfoNew.setBankAccountType(partnerInfo.getBankAccountType());
        partnerInfoNew.setBankType(partnerInfo.getBankType());
        partnerInfoNew.setBankName(partnerInfo.getBankName());
        partnerInfoNew.setState(partnerInfo.getState());
        return this.editDataAudit(partnerInfoDb, partnerInfoNew);
    }

}
