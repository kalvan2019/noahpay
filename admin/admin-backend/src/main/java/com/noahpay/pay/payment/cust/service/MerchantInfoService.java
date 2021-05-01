package com.noahpay.pay.payment.cust.service;

import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.MerchantInfoMapper;
import com.noahpay.pay.commons.db.cust.model.MerchantInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商户信息Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("merchantInfoService")
public class MerchantInfoService extends BaseAuditService<MerchantInfo> {
    @Resource
    MerchantInfoMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(MerchantInfo merchantInfo) {
        MerchantInfo merchantInfoQuery = new MerchantInfo();
        // TODO 填充数据,选择字段进行检查，更新人，更新时间，查重
        if (mapper.selectCount(merchantInfoQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        merchantInfo.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(merchantInfo);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(MerchantInfo merchantInfo) {
        MerchantInfo merchantInfoDb = mapper.selectByUk(merchantInfo.getId());
        if (merchantInfoDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用merchantInfo
        MerchantInfo merchantInfoNew = new MerchantInfo();
        merchantInfoNew.setId(merchantInfo.getId());
        merchantInfoNew.setMerchantNo(merchantInfo.getMerchantNo());
        merchantInfoNew.setMerchantName(merchantInfo.getMerchantName());
        merchantInfoNew.setCustNo(merchantInfo.getCustNo());
        merchantInfoNew.setBusinessLicenseNo(merchantInfo.getBusinessLicenseNo());
        merchantInfoNew.setBankAccountNo(merchantInfo.getBankAccountNo());
        merchantInfoNew.setBankAccountName(merchantInfo.getBankAccountName());
        merchantInfoNew.setBankAccountType(merchantInfo.getBankAccountType());
        merchantInfoNew.setBankType(merchantInfo.getBankType());
        merchantInfoNew.setBankName(merchantInfo.getBankName());
        merchantInfoNew.setState(merchantInfo.getState());
        return this.editDataAudit(merchantInfoDb, merchantInfoNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            MerchantInfo merchantInfoDb = mapper.selectByUk(id);
            if (merchantInfoDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(merchantInfoDb);
        }
        return resultNum;
    }
}
