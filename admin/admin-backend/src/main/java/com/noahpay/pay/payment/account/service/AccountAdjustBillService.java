package com.noahpay.pay.payment.account.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.account.mapper.AccountAdjustBillMapper;
import com.noahpay.pay.commons.db.account.model.AccountAdjustBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 账户调账流水Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("accountAdjustBillService")
public class AccountAdjustBillService extends BaseAuditService<AccountAdjustBill> {
    @Resource
    AccountAdjustBillMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.ACCOUNT_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(AccountAdjustBill accountAdjustBill) {
        AccountAdjustBill accountAdjustBillQuery = new AccountAdjustBill();
        // TODO 填充数据,选择字段进行检查，更新人，更新时间，查重
        if (mapper.selectCount(accountAdjustBillQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        accountAdjustBill.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(accountAdjustBill);
    }

}
