package com.noahpay.pay.payment.settle.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.settle.mapper.AccountSummaryDayMapper;
import com.noahpay.pay.commons.db.settle.model.AccountSummaryDay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 账户余额日终报表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("accountSummaryDayService")
public class AccountSummaryDayService extends BaseAuditService<AccountSummaryDay> {
    @Resource
    AccountSummaryDayMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

}
