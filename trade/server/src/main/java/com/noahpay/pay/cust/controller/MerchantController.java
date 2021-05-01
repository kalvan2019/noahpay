package com.noahpay.pay.cust.controller;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.cust.bean.req.MerchantCheckTransRequest;
import com.noahpay.pay.cust.bean.req.MerchantRegisterRequest;
import com.noahpay.pay.cust.bean.res.MerchantCheckTransResponse;
import com.noahpay.pay.cust.bean.res.MerchantQueryResponse;
import com.noahpay.pay.cust.bean.res.MerchantRegisterResponse;
import com.noahpay.pay.cust.iface.IMerchant;
import com.noahpay.pay.cust.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author chenliang
 */
@Slf4j
@RestController
public class MerchantController implements IMerchant {
    @Resource
    MerchantService merchantService;

    @Override
    public Response<MerchantRegisterResponse> merchantRegister(@Valid Request<MerchantRegisterRequest> request) {
        return merchantService.register(request);
    }

    @Override
    public Response<MerchantQueryResponse> merchantQuery(@Valid Request<Long> request) {
        return null;
    }

    @Override
    public Response<MerchantCheckTransResponse> merchantCheckTrans(@Valid Request<MerchantCheckTransRequest> request) {
        return merchantService.checkTrans(request);
    }
}
