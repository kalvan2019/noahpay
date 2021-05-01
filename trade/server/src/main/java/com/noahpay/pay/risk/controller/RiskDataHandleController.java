package com.noahpay.pay.risk.controller;

import com.noahpay.pay.risk.service.RiskTransService;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.risk.bean.req.MerchantUseRequest;
import com.noahpay.pay.risk.iface.IRiskDataHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author chenliang
 */
@Slf4j
@RestController
public class RiskDataHandleController implements IRiskDataHandle {
    @Resource
    private RiskTransService riskTransService;

    @Override
    public Response updateMerchantUse(@Valid Request<MerchantUseRequest> request) {
        riskTransService.updateMerchantDayUseAmount(request.getData());
        return Response.buildSuccess();
    }
}