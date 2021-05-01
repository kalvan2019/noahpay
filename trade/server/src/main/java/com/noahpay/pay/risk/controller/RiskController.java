package com.noahpay.pay.risk.controller;

import com.noahpay.pay.risk.service.RiskBlackService;
import com.noahpay.pay.risk.service.RiskTransService;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.risk.bean.req.RiskBlackRequest;
import com.noahpay.pay.risk.bean.req.RiskTransRequest;
import com.noahpay.pay.risk.bean.res.RiskResponse;
import com.noahpay.pay.risk.iface.IRisk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author chenliang
 */
@Slf4j
@RestController
public class RiskController implements IRisk {
    @Resource
    RiskBlackService riskBlackService;
    @Resource
    private RiskTransService riskTransService;

    @Override
    public Response<RiskResponse> checkBlack(@Valid Request<RiskBlackRequest> request) {
        return riskBlackService.checkBlack(request.getData());
    }

    @Override
    public Response<RiskResponse> checkTrans(@Valid Request<RiskTransRequest> request) {
        return riskTransService.checkRiskTrade(request.getData());
    }

}