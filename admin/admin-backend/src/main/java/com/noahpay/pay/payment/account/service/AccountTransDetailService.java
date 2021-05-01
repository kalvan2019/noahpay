package com.noahpay.pay.payment.account.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.account.mapper.AccountTransDetailMapper;
import com.noahpay.pay.commons.db.account.model.AccountTransDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 记账明细Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("accountTransDetailService")
public class AccountTransDetailService extends BaseAuditService<AccountTransDetail> {
    @Resource
    AccountTransDetailMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

}
