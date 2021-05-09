package com.noahpay.pay.trade.controller;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.trade.bean.req.OrderQueryRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.constant.TransStateEnum;
import com.noahpay.pay.trade.iface.IPayQuery;
import com.noahpay.pay.trade.process.trans.ChannelTransFactory;
import com.noahpay.pay.trade.service.PayBillService;
import com.noahpay.pay.trade.service.TransService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author chenliang
 */
@RestController
@Slf4j
public class PayQueryController implements IPayQuery {
    @Resource
    private PayBillService payBillService;
    @Resource
    private TransService transService;
    @Resource
    private ChannelTransFactory channelTransFactory;

    @Override
    public Response<TransResponse> query(@Valid Request<OrderQueryRequest> request) {
        OrderQueryRequest orderQueryRequest = request.getData();
        PayBill payBill;
        if (StringUtils.isNotBlank(orderQueryRequest.getTransId())) {
            payBill = payBillService.queryByTransId(orderQueryRequest.getTransId());
        } else {
            payBill = payBillService.queryByOrderId(orderQueryRequest.getMerchantNo(), orderQueryRequest.getOrderDate(), orderQueryRequest.getOrderId());
        }
        if (payBill.getState() != TransStateEnum.SUCCESS.code && payBill.getState() != TransStateEnum.FAIL.code) {
            //向渠道同步查询
            payBill = channelTransFactory.getChannelTrans(payBill).channelQuery(payBill);
        }
        return transService.getResult(payBill);
    }
}