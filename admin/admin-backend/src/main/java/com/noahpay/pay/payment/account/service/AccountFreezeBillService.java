package com.noahpay.pay.payment.account.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.account.mapper.AccountFreezeBillMapper;
import com.noahpay.pay.commons.db.account.model.AccountFreezeBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 账户冻结解冻明细Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("accountFreezeBillService")
public class AccountFreezeBillService extends BaseAuditService<AccountFreezeBill> {
    @Resource
    AccountFreezeBillMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.ACCOUNT_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(AccountFreezeBill accountFreezeBill) {
        AccountFreezeBill accountFreezeBillQuery = new AccountFreezeBill();
        // TODO 填充数据,选择字段进行检查，更新人，更新时间，查重
        if (mapper.selectCount(accountFreezeBillQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        accountFreezeBill.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(accountFreezeBill);
    }

}
