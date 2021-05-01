package com.noahpay.pay.fee.controller;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.fee.bean.req.FeeCalculateRequest;
import com.noahpay.pay.fee.bean.res.FeeCalculateResponse;
import com.noahpay.pay.fee.iface.IFee;
import com.noahpay.pay.fee.service.FeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author chenliang
 */
@Slf4j
@RestController
public class FeeController implements IFee {
    @Resource
    FeeService feeService;

    @Override
    public Response<FeeCalculateResponse> feeCalculate(@Valid Request<FeeCalculateRequest> request) {
        return feeService.calculateFee(request);
    }
}
