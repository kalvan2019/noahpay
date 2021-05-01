package com.noahpay.pay.payment.trade.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.trade.mapper.TransPayBillMapper;
import com.noahpay.pay.commons.db.trade.model.TransPayBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 交易订单支付明细Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("transPayBillService")
public class TransPayBillService extends BaseAuditService<TransPayBill> {
    @Resource
    TransPayBillMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "orderDate", false, true);
        convertBetweenParams(params, "createTime", true, false);
    }

}
