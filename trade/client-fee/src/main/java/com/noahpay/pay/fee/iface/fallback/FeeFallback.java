package com.noahpay.pay.fee.iface.fallback;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.fee.bean.req.FeeCalculateRequest;
import com.noahpay.pay.fee.bean.res.FeeCalculateResponse;
import com.noahpay.pay.fee.constant.FeeReturnCode;
import com.noahpay.pay.fee.iface.IFee;

import javax.validation.Valid;

/**
 * @author chenliang
 */
public class FeeFallback implements IFee {
    @Override
    public Response<FeeCalculateResponse> feeCalculate(@Valid Request<FeeCalculateRequest> request) {
        return Response.buildResult(FeeReturnCode.FALLBACK);
    }
}
