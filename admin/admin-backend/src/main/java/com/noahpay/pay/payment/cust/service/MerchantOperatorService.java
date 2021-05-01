package com.noahpay.pay.payment.cust.service;

import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.MerchantOperatorMapper;
import com.noahpay.pay.commons.db.cust.model.MerchantOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商户操作员表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("merchantOperatorService")
public class MerchantOperatorService extends BaseAuditService<MerchantOperator> {
    @Resource
    MerchantOperatorMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(MerchantOperator merchantOperator) {
        //TODO 调用客户系统生成操作员号
        merchantOperator.setOperatorNo(1000L);
        merchantOperator.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(merchantOperator);
    }

    @Override
    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(MerchantOperator merchantOperator) {
        MerchantOperator merchantOperatorDb = mapper.selectByUk(merchantOperator.getId());
        if (merchantOperatorDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        MerchantOperator merchantOperatorNew = new MerchantOperator();
        merchantOperatorNew.setId(merchantOperator.getId());
        merchantOperatorNew.setOperatorName(merchantOperator.getOperatorName());
        merchantOperatorNew.setState(merchantOperator.getState());
        return this.editDataAudit(merchantOperatorDb, merchantOperatorNew);
    }

}
