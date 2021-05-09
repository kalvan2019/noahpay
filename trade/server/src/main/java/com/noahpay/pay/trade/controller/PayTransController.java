package com.noahpay.pay.trade.controller;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.trade.bean.req.MicroPayRequest;
import com.noahpay.pay.trade.bean.req.UnifiedOrderRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.iface.IPayTrans;
import com.noahpay.pay.trade.service.TransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenliang
 */
@RestController
@Slf4j
public class PayTransController implements IPayTrans {
    @Resource
    private TransService transService;

    @Override
    public Response<TransResponse> unifiedOrder(Request<UnifiedOrderRequest> request) {
        return transService.order(request.getData());
    }

    @Override
    public Response<TransResponse> microPay(Request<MicroPayRequest> request) {
        return transService.order(request.getData());
    }
}