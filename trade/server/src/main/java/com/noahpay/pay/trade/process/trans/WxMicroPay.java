package com.noahpay.pay.trade.process.trans;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.JSON;
import com.kalvan.client.constant.CommonStateEnum;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Response;
import com.noahpay.pay.channel.bean.res.ChannelTransResponse;
import com.noahpay.pay.channel.constant.ChannelReturnCode;
import com.noahpay.pay.channel.wx.WxClient;
import com.noahpay.pay.channel.wx.WxRequest;
import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import com.noahpay.pay.channel.wx.model.ExtDataInfo;
import com.noahpay.pay.channel.wx.request.WxPayMicroPay;
import com.noahpay.pay.channel.wx.response.pay.WxPayMicropayResponse;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.route.constant.ChannelExtParamKey;
import com.noahpay.pay.route.service.ChannelService;
import com.noahpay.pay.trade.constant.TransReturnCode;
import com.noahpay.pay.trade.constant.TransStateEnum;
import com.noahpay.pay.trade.process.template.BaseChannelTrans;
import com.noahpay.pay.trade.service.PayBillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenliang
 */
@Component
@Slf4j
public class WxMicroPay extends BaseChannelTrans<PayBill> {
    @Resource
    WxPay wxPay;
    @Resource
    PayBillService payBillService;
    @Resource
    ChannelService channelService;
    @Value("${server.ip}")
    String ip;

    @Override
    public void channelConvertParam(PayBill bill) {
        wxPay.channelConvertParam(bill);
    }

    @Override
    public PayBill channelTrans(PayBill bill) {
        String appId = channelService.getChannelExtParamValue(bill.getChannelNo(), bill.getChannelMerchantNo(), ChannelExtParamKey.APP_ID.code);
        if (StringUtils.isBlank(appId)) {
            throw new BizException(ChannelReturnCode.CODE_6001.formatMessage(ChannelExtParamKey.APP_ID.code));
        }
        String apiKey = channelService.getChannelExtParamValue(bill.getChannelNo(), bill.getChannelMerchantNo(), ChannelExtParamKey.API_KEY.code);
        if (StringUtils.isBlank(apiKey)) {
            throw new BizException(ChannelReturnCode.CODE_6001.formatMessage(ChannelExtParamKey.API_KEY.code));
        }
        String notifyUrl = channelService.getChannelExtParamValue(bill.getChannelNo(), bill.getChannelMerchantNo(), ChannelExtParamKey.NOTIFY_URL.code);
        if (StringUtils.isBlank(notifyUrl)) {
            throw new BizException(ChannelReturnCode.CODE_6001.formatMessage(ChannelExtParamKey.NOTIFY_URL.code));
        }
        notifyUrl = notifyUrl + "/" + bill.getChannelNo() + "/" + bill.getPayType();
        //请求微信支付
        WxPayMicroPay unifiedOrder = new WxPayMicroPay();
        unifiedOrder.setBody(bill.getDescription());
//        unifiedOrder.setDetail();
        unifiedOrder.setAttach(bill.getAttach());
        unifiedOrder.setOut_trade_no(bill.getChannelSendSn());
        unifiedOrder.setTotal_fee(String.valueOf(bill.getChannelAmount()));
        unifiedOrder.setFee_type(bill.getCurrency());
        unifiedOrder.setSpbill_create_ip(ip);
        if (bill.getTimeStart() != null) {
            unifiedOrder.setTime_start(DatePattern.PURE_DATETIME_FORMAT.format(bill.getTimeStart()));
        }
        if (bill.getTimeExpire() != null) {
            unifiedOrder.setTime_expire(DatePattern.PURE_DATETIME_FORMAT.format(bill.getTimeExpire()));
        }
//        unifiedOrder.setDevice_info();
//        unifiedOrder.setSub_appid();
        unifiedOrder.setAppid(appId);
        unifiedOrder.setMch_id(bill.getChannelMerchantNo());
        unifiedOrder.setSub_mch_id(bill.getChannelSubMerchantNo());
        //付款条码
        unifiedOrder.setAuth_code(bill.getChannelCodeUrl());
        //发送请求
        WxRequest wxRequest = new WxRequest(unifiedOrder, WxPayMicropayResponse.class, WxPayConstants.MICROPAY_URL_SUFFIX).setKey(apiKey);
        WxPayMicropayResponse wxResponse = WxClient.execute(wxRequest);
        if (wxResponse == null) {
            //没有获取到支付结果当超时
            Response response = Response.buildResult(TransReturnCode.OVERTIME).setState(TransStateEnum.OVERTIME.code);
            return payBillService.updateChannelResponse(bill, response);
        }
        if (wxResponse.isSuccess()) {
            //通信成功
            ChannelTransResponse channelResponse = new ChannelTransResponse();
            channelResponse.setChannelNo(bill.getChannelNo());
            channelResponse.setChannelSendSn(wxResponse.getOutTradeNo());
            channelResponse.setChannelRecvSn(wxResponse.getTransactionId());
            if (StringUtils.isNotBlank(wxResponse.getTotalFee())) {
                channelResponse.setChannelRecvAmount(Long.parseLong(wxResponse.getTotalFee()));
            }
            ExtDataInfo extDataInfo = new ExtDataInfo();
            extDataInfo.setOpenid(wxResponse.getOpenid());
            extDataInfo.setIsSubscribe(wxResponse.getIsSubscribe());
            extDataInfo.setSubOpenid(wxResponse.getSubOpenid());
            extDataInfo.setSubIsSubscribe(wxResponse.getSubIsSubscribe());
            channelResponse.setChannelRecvExt(JSON.toJSONString(extDataInfo));
            Response response;
            if (WxPayConstants.SUCCESS.equals(wxResponse.getResultCode())) {
                //明确交易成功
                response = Response.buildSuccess().setData(channelResponse);
            } else {
                //明确交易失败
                response = Response.buildResult(wxResponse.getErrCode(), wxResponse.getErrCodeDes()).setData(channelResponse);
            }
            //更新支付结果
            return payBillService.updateChannelResponse(bill, response);
        } else {
            //当失败
            // <xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[不识别的参数notify_url]]></return_msg></xml>
            Response response = Response.buildResult(wxResponse.getReturnCode(), wxResponse.getReturnMsg());
            response.setState(CommonStateEnum.FAIL.code);
            return payBillService.updateChannelResponse(bill, response);
        }
    }

    @Override
    public PayBill channelQuery(PayBill bill) {
        return wxPay.channelQuery(bill);
    }
}
