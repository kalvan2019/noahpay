package com.noahpay.pay.trade.iface.fallback;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.trade.bean.req.MicroPayRequest;
import com.noahpay.pay.trade.bean.req.OrderRequest;
import com.noahpay.pay.trade.bean.req.UnifiedOrderRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.constant.TransReturnCode;
import com.noahpay.pay.trade.constant.TransStateEnum;
import com.noahpay.pay.trade.iface.IPayTrans;

/**
 * @author chenliang
 */
public class TradeFallback implements IPayTrans {

    @Override
    public Response<TransResponse> unifiedOrder(Request<UnifiedOrderRequest> request) {
        return Response.buildResult(TransReturnCode.FALLBACK).setState(TransStateEnum.FAIL.code);
    }

    @Override
    public Response<TransResponse> microPay(Request<MicroPayRequest> request) {
        return Response.buildResult(TransReturnCode.FALLBACK).setState(TransStateEnum.OVERTIME.code);
    }
}
