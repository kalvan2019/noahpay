package com.noahpay.pay.payment.risk.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.risk.mapper.BlackListMapper;
import com.noahpay.pay.commons.db.risk.model.BlackList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 黑名单Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("blackListService")
public class BlackListService extends BaseAuditService<BlackList> {
    @Resource
    BlackListMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.RISK_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(BlackList blackList) {
        blackList.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(blackList);
    }

    @Override
    @Transactional(value = DataSourceConstants.RISK_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(BlackList blackList) {
        BlackList blackListDb = mapper.selectByUk(blackList.getId());
        if (blackListDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用blackList
        BlackList blackListNew = new BlackList();
        blackListNew.setId(blackList.getId());
        blackListNew.setBlackType(blackList.getBlackType());
        blackListNew.setCustNo(blackList.getCustNo());
        blackListNew.setCustName(blackList.getCustName());
        blackListNew.setCertificateNo(blackList.getCertificateNo());
        blackListNew.setBankAccountNo(blackList.getBankAccountNo());
        blackListNew.setEmail(blackList.getEmail());
        blackListNew.setMobile(blackList.getMobile());
        blackListNew.setBusinessLicenseNo(blackList.getBusinessLicenseNo());
        blackListNew.setState(blackList.getState());
        return this.editDataAudit(blackListDb, blackListNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.RISK_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            BlackList blackListDb = mapper.selectByUk(id);
            if (blackListDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(blackListDb);
        }
        return resultNum;
    }
}
