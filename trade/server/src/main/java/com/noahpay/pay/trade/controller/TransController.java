package com.noahpay.pay.trade.controller;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.trade.bean.req.TransRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.iface.ITrans;
import com.noahpay.pay.trade.service.TransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenliang
 */
@RestController
@Slf4j
public class TransController implements ITrans {
    @Resource
    private TransService transService;

    @Override
    public Response<TransResponse> order(Request<TransRequest> request) {
        return transService.order(request.getData());
    }
}