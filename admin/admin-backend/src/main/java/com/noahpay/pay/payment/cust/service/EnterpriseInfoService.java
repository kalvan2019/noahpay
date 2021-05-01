package com.noahpay.pay.payment.cust.service;

import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.EnterpriseInfoMapper;
import com.noahpay.pay.commons.db.cust.model.EnterpriseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 企业客户信息Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("enterpriseInfoService")
public class EnterpriseInfoService extends BaseAuditService<EnterpriseInfo> {
    @Resource
    EnterpriseInfoMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(EnterpriseInfo enterpriseInfo) {
        EnterpriseInfo enterpriseInfoDb = mapper.selectByUk(enterpriseInfo.getId());
        if (enterpriseInfoDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        EnterpriseInfo enterpriseInfoNew = new EnterpriseInfo();
        enterpriseInfoNew.setId(enterpriseInfo.getId());
        enterpriseInfoNew.setBusinessLicenseNo(enterpriseInfo.getBusinessLicenseNo());
        enterpriseInfoNew.setBusinessScope(enterpriseInfo.getBusinessScope());
        enterpriseInfoNew.setBusinessAmount(enterpriseInfo.getBusinessAmount());
        enterpriseInfoNew.setBusinessLicenseExpiry(enterpriseInfo.getBusinessLicenseExpiry());
        enterpriseInfoNew.setCompanyName(enterpriseInfo.getCompanyName());
        enterpriseInfoNew.setCompanyAddress(enterpriseInfo.getCompanyAddress());
        enterpriseInfoNew.setIndustry(enterpriseInfo.getIndustry());
        enterpriseInfoNew.setWebsite(enterpriseInfo.getWebsite());
        enterpriseInfoNew.setTelephone(enterpriseInfo.getTelephone());
        enterpriseInfoNew.setState(enterpriseInfo.getState());
        enterpriseInfoNew.setCloseTime(enterpriseInfo.getCloseTime());
        return this.editDataAudit(enterpriseInfoDb, enterpriseInfoNew);
    }

}
