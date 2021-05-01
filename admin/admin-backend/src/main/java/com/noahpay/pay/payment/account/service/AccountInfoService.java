package com.noahpay.pay.payment.account.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.account.mapper.AccountInfoMapper;
import com.noahpay.pay.commons.db.account.model.AccountInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 账户钱包Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("accountInfoService")
public class AccountInfoService extends BaseAuditService<AccountInfo> {
    @Resource
    AccountInfoMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "amount", false, false);
        convertBetweenParams(params, "updateTime", true, false);
    }

}
