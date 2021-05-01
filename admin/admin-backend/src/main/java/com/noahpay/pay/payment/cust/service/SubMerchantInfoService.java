package com.noahpay.pay.payment.cust.service;

import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.SubMerchantInfoMapper;
import com.noahpay.pay.commons.db.cust.model.SubMerchantInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 子商户信息Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("subMerchantInfoService")
public class SubMerchantInfoService extends BaseAuditService<SubMerchantInfo> {
    @Resource
    SubMerchantInfoMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(SubMerchantInfo subMerchantInfo) {
        //TODO 调用客户系统生成子商户号
        subMerchantInfo.setSubMerchantNo(1000L);
        subMerchantInfo.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(subMerchantInfo);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(SubMerchantInfo subMerchantInfo) {
        SubMerchantInfo subMerchantInfoDb = mapper.selectByUk(subMerchantInfo.getId());
        if (subMerchantInfoDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用subMerchantInfo
        SubMerchantInfo subMerchantInfoNew = new SubMerchantInfo();
        subMerchantInfoNew.setId(subMerchantInfo.getId());
        subMerchantInfoNew.setSubMerchantNo(subMerchantInfo.getSubMerchantNo());
        subMerchantInfoNew.setSubMerchantName(subMerchantInfo.getSubMerchantName());
        subMerchantInfoNew.setCustNo(subMerchantInfo.getCustNo());
        subMerchantInfoNew.setMerchantNo(subMerchantInfo.getMerchantNo());
        subMerchantInfoNew.setBankAccountNo(subMerchantInfo.getBankAccountNo());
        subMerchantInfoNew.setBankAccountName(subMerchantInfo.getBankAccountName());
        subMerchantInfoNew.setBankAccountType(subMerchantInfo.getBankAccountType());
        subMerchantInfoNew.setBankType(subMerchantInfo.getBankType());
        subMerchantInfoNew.setBankName(subMerchantInfo.getBankName());
        subMerchantInfoNew.setState(subMerchantInfo.getState());
        return this.editDataAudit(subMerchantInfoDb, subMerchantInfoNew);
    }

}
