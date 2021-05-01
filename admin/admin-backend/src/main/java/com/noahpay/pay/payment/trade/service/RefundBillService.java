package com.noahpay.pay.payment.trade.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.trade.mapper.RefundBillMapper;
import com.noahpay.pay.commons.db.trade.model.RefundBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 退款交易流水Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("refundBillService")
public class RefundBillService extends BaseAuditService<RefundBill> {
    @Resource
    RefundBillMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
    }

}
