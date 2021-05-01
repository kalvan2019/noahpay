package com.noahpay.pay.payment.settle.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.settle.mapper.AccountReportDayMapper;
import com.noahpay.pay.commons.db.settle.model.AccountReportDay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 账户汇总日报表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("accountReportDayService")
public class AccountReportDayService extends BaseAuditService<AccountReportDay> {
    @Resource
    AccountReportDayMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

}
