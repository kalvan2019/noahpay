package com.noahpay.pay.trade.controller;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.channel.bean.res.ChannelTransResponse;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.trade.bean.req.CallBackRequest;
import com.noahpay.pay.trade.bean.req.OrderNotifyRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.iface.INotify;
import com.noahpay.pay.trade.service.PayBillService;
import com.noahpay.pay.trade.service.TransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author chenliang
 */
@RestController
@Slf4j
public class NotifyController implements INotify {
    @Resource
    private PayBillService payBillService;
    @Resource
    private TransService transService;

    @Override
    public Response<TransResponse> callback(@Valid Request<CallBackRequest> request) {
        CallBackRequest callBackRequest = request.getData();
        ChannelTransResponse channelTransResponse = new ChannelTransResponse();
        channelTransResponse.setChannelNo(callBackRequest.getChannelNo());
        channelTransResponse.setChannelSendSn(callBackRequest.getChannelSendSn());
        channelTransResponse.setChannelRecvSn(callBackRequest.getChannelRecvSn());
        channelTransResponse.setChannelRecvAmount(callBackRequest.getChannelRecvAmount());
        channelTransResponse.setChannelRecvAccountDate(callBackRequest.getChannelRecvAccountDate());
        channelTransResponse.setChannelRecvExt(callBackRequest.getChannelRecvExt());
        Response response = Response.buildResult(callBackRequest.getCode(), callBackRequest.getMessage());
        response.setState(callBackRequest.getState());
        response.setData(channelTransResponse);
        PayBill bill = payBillService.updateChannelResponse(null, response);
        transService.notify(bill);
        return transService.getResult(bill);
    }

    @Override
    public Response notifyMerchant(@Valid Request<String> request) {
        PayBill bill = payBillService.queryByTransId(request.getData());
        transService.notify(bill);
        return Response.buildSuccess();
    }

    @Override
    public Response updateNotifyState(@Valid Request<OrderNotifyRequest> request) {
        payBillService.updateNotifyState(request.getData().getTransId(), request.getData().getNotifyState());
        return Response.buildSuccess();
    }
}