package com.noahpay.pay.payment.account.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.account.mapper.AccountTransBatchMapper;
import com.noahpay.pay.commons.db.account.model.AccountTransBatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 账户记账事务控制表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("accountTransBatchService")
public class AccountTransBatchService extends BaseAuditService<AccountTransBatch> {
    @Resource
    AccountTransBatchMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "accountDate", false, false);
    }

}
