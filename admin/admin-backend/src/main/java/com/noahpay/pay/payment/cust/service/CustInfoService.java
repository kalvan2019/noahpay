package com.noahpay.pay.payment.cust.service;

import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.CustInfoMapper;
import com.noahpay.pay.commons.db.cust.model.CustInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 客户信息Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("custInfoService")
public class CustInfoService extends BaseAuditService<CustInfo> {
    @Resource
    CustInfoMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(CustInfo custInfo) {
        CustInfo custInfoDb = mapper.selectByUk(custInfo.getCustNo());
        if (custInfoDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        CustInfo custInfoNew = new CustInfo();
        custInfoNew.setCustNo(custInfo.getCustNo());
        custInfoNew.setCertificateNo(custInfo.getCertificateNo());
        custInfoNew.setCertificateName(custInfo.getCertificateName());
        custInfoNew.setCertificateType(custInfo.getCertificateType());
        custInfoNew.setCertificateAddress(custInfo.getCertificateAddress());
        custInfoNew.setCertificateExpiry(custInfo.getCertificateExpiry());
        custInfoNew.setEmail(custInfo.getEmail());
        custInfoNew.setMobile(custInfo.getMobile());
        custInfoNew.setAddress(custInfo.getAddress());
        custInfoNew.setState(custInfo.getState());
        return this.editDataAudit(custInfoDb, custInfoNew);
    }

}
