package com.noahpay.pay.risk.iface.fallback;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.risk.bean.req.MerchantUseRequest;
import com.noahpay.pay.risk.bean.req.RiskBlackRequest;
import com.noahpay.pay.risk.bean.req.RiskTransRequest;
import com.noahpay.pay.risk.bean.res.RiskResponse;
import com.noahpay.pay.risk.constant.RiskReturnCode;
import com.noahpay.pay.risk.iface.IRisk;
import com.noahpay.pay.risk.iface.IRiskDataHandle;

import javax.validation.Valid;

/**
 * @author chenliang
 */
public class RiskFallback implements IRisk, IRiskDataHandle {
    @Override
    public Response<RiskResponse> checkBlack(@Valid Request<RiskBlackRequest> request) {
        return Response.buildResult(RiskReturnCode.FALLBACK);
    }

    @Override
    public Response<RiskResponse> checkTrans(@Valid Request<RiskTransRequest> request) {
        return Response.buildResult(RiskReturnCode.FALLBACK);
    }

    @Override
    public Response updateMerchantUse(@Valid Request<MerchantUseRequest> request) {
        return Response.buildResult(RiskReturnCode.FALLBACK);
    }
}
