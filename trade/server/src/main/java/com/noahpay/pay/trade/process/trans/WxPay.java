package com.noahpay.pay.trade.process.trans;

import com.kalvan.client.constant.CommonStateEnum;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Response;
import com.kalvan.web.util.DateUtil;
import com.noahpay.pay.channel.bean.res.ChannelTransResponse;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.trade.process.template.BaseChannelTrans;
import com.noahpay.pay.trade.service.PayBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenliang
 */
@Component
@Slf4j
public class WxPay extends BaseChannelTrans<PayBill> {
    @Resource
    PayBillService payBillService;

    @Override
    public void channelConvertParam(PayBill bill) {
        bill.setChannelSendSn(bill.getTransId());
        bill.setChannelSendTime(DateUtil.date());
        payBillService.updateSendChannel(bill);
    }

    @Override
    public PayBill channelTrans(PayBill bill) {
        //TODO 调用通道支付接口
        ChannelTransResponse channelResponse = new ChannelTransResponse();
        Response response = Response.buildSuccess().setData(channelResponse);
        //刷卡交易需要更新数据库
        return payBillService.updateChannelResponse(bill, response);
    }

    @Override
    public PayBill channelQuery(PayBill payBill) {
        ChannelTransResponse channelResponse = new ChannelTransResponse();
        Response response = Response.buildSuccess().setData(channelResponse);
        if (CommonStateEnum.QUERY_FAIL.code == response.getState()) {
            throw new BizException(response);
        }
        return payBillService.updateChannelResponse(payBill, response);
    }
}
