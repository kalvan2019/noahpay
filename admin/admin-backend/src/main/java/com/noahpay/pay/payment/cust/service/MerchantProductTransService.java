package com.noahpay.pay.payment.cust.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.MerchantProductTransMapper;
import com.noahpay.pay.commons.db.cust.model.MerchantProductTrans;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商户交易业务入网Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("merchantProductTransService")
public class MerchantProductTransService extends BaseAuditService<MerchantProductTrans> {
    @Resource
    MerchantProductTransMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(MerchantProductTrans merchantProductTrans) {
        MerchantProductTrans merchantProductTransQuery = new MerchantProductTrans();
        merchantProductTransQuery.setMerchantNo(merchantProductTrans.getMerchantNo());
        merchantProductTransQuery.setTransType(merchantProductTrans.getTransType());
        if (mapper.selectCount(merchantProductTransQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        merchantProductTrans.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(merchantProductTrans);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(MerchantProductTrans merchantProductTrans) {
        MerchantProductTrans merchantProductTransDb = mapper.selectByUk(merchantProductTrans.getId());
        if (merchantProductTransDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用merchantProductTrans
        MerchantProductTrans merchantProductTransNew = new MerchantProductTrans();
        merchantProductTransNew.setId(merchantProductTrans.getId());
        merchantProductTransNew.setMerchantNo(merchantProductTrans.getMerchantNo());
        merchantProductTransNew.setTransType(merchantProductTrans.getTransType());
        merchantProductTransNew.setLimitMaxAmount(merchantProductTrans.getLimitMaxAmount());
        merchantProductTransNew.setDayLimitAmount(merchantProductTrans.getDayLimitAmount());
        merchantProductTransNew.setDayLimitNumber(merchantProductTrans.getDayLimitNumber());
        merchantProductTransNew.setMonthLimitAmount(merchantProductTrans.getMonthLimitAmount());
        merchantProductTransNew.setMonthLimitNumber(merchantProductTrans.getMonthLimitNumber());
        merchantProductTransNew.setEffectiveDate(merchantProductTrans.getEffectiveDate());
        merchantProductTransNew.setExpiryDate(merchantProductTrans.getExpiryDate());
        merchantProductTransNew.setSignAuditType(merchantProductTrans.getSignAuditType());
        merchantProductTransNew.setSignCheckSms(merchantProductTrans.getSignCheckSms());
        merchantProductTransNew.setSignSendSms(merchantProductTrans.getSignSendSms());
        merchantProductTransNew.setTransDealType(merchantProductTrans.getTransDealType());
        merchantProductTransNew.setTransAuditType(merchantProductTrans.getTransAuditType());
        merchantProductTransNew.setTransCheckProtocol(merchantProductTrans.getTransCheckProtocol());
        merchantProductTransNew.setTransCheckSms(merchantProductTrans.getTransCheckSms());
        merchantProductTransNew.setTransSendSms(merchantProductTrans.getTransSendSms());
        merchantProductTransNew.setState(merchantProductTrans.getState());
        return this.editDataAudit(merchantProductTransDb, merchantProductTransNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            MerchantProductTrans merchantProductTransDb = mapper.selectByUk(id);
            if (merchantProductTransDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(merchantProductTransDb);
        }
        return resultNum;
    }
}
