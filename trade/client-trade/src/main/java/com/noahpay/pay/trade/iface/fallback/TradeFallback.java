package com.noahpay.pay.trade.iface.fallback;

import com.kalvan.client.model.ApiRequest;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.trade.bean.req.TransRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.constant.TransReturnCode;
import com.noahpay.pay.trade.constant.TransStateEnum;
import com.noahpay.pay.trade.iface.ITrans;

/**
 * @author chenliang
 */
public class TradeFallback implements ITrans {

    @Override
    public Response<TransResponse> order(ApiRequest<TransRequest> request) {
        return Response.buildResult(TransReturnCode.FALLBACK).setState(TransStateEnum.FAIL.code);
    }
}
