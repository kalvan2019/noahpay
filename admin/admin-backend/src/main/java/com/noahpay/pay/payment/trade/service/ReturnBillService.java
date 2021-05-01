package com.noahpay.pay.payment.trade.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.trade.mapper.ReturnBillMapper;
import com.noahpay.pay.commons.db.trade.model.ReturnBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 退票流水Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("returnBillService")
public class ReturnBillService extends BaseAuditService<ReturnBill> {
    @Resource
    ReturnBillMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ReturnBill returnBill) {
        return this.addDataAudit(returnBill);
    }

}
