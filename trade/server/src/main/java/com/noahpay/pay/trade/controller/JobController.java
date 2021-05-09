package com.noahpay.pay.trade.controller;

import com.kalvan.client.constant.CommonStateEnum;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.channel.bean.res.ChannelTransResponse;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.trade.bean.req.OrderManualRequest;
import com.noahpay.pay.trade.constant.TransReturnCode;
import com.noahpay.pay.trade.constant.TransStateEnum;
import com.noahpay.pay.trade.iface.IJob;
import com.noahpay.pay.trade.process.trans.ChannelTransFactory;
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
public class JobController implements IJob {
    @Resource
    private TransService transService;
    @Resource
    private PayBillService payBillService;
    @Resource
    private ChannelTransFactory channelTransFactory;

    @Override
    public Response synQuery(@Valid Request<String> request) {
        String transId = request.getData();
        PayBill payBill = payBillService.queryByTransId(transId);
        if (payBill.getState() == TransStateEnum.SUCCESS.code ||
                payBill.getState() == TransStateEnum.FAIL.code) {
            return Response.buildResult(TransReturnCode.DATA_STATE_ERROR);
        }
        PayBill bill = channelTransFactory.getChannelTrans(payBill).channelQuery(payBill);
        transService.notify(bill);
        if (bill.getState() == TransStateEnum.SUCCESS.code
                || bill.getState() == TransStateEnum.SUCCESS_PROCESS.code
                || bill.getState() == TransStateEnum.FAIL.code
                || bill.getState() == TransStateEnum.FAIL_PROCESS.code
        ) {
            return Response.buildSuccess();
        }
        return Response.buildResult(TransReturnCode.PROCESS);
    }

    @Override
    public Response manualHandling(@Valid Request<OrderManualRequest> request) {
        OrderManualRequest orderManualRequest = request.getData();
        PayBill payBill = payBillService.queryByTransId(orderManualRequest.getTransId());
        if (payBill.getState() == TransStateEnum.SUCCESS.code ||
                payBill.getState() == TransStateEnum.FAIL.code) {
            return Response.buildResult(TransReturnCode.DATA_STATE_ERROR);
        }
        ChannelTransResponse channelTransResponse = new ChannelTransResponse();
        channelTransResponse.setChannelNo(payBill.getChannelNo());
        channelTransResponse.setChannelSendSn(payBill.getChannelSendSn());
        Response response = Response.buildResult(TransReturnCode.CODE_MANUAL.formatMessage(orderManualRequest.getRemark()));
        if (orderManualRequest.getSuccess()) {
            response.setState(CommonStateEnum.SUCCESS.code);
        } else {
            response.setState(CommonStateEnum.FAIL.code);
        }
        response.setData(channelTransResponse);
        PayBill bill = payBillService.updateChannelResponse(payBill, response);
        transService.notify(bill);
        return Response.buildSuccess();
    }
}