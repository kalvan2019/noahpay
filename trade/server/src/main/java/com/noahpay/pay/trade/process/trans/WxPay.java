package com.noahpay.pay.trade.process.trans;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.JSON;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Response;
import com.kalvan.web.util.DateUtil;
import com.noahpay.pay.channel.bean.res.ChannelTransResponse;
import com.noahpay.pay.channel.constant.ChannelReturnCode;
import com.noahpay.pay.channel.wx.WxClient;
import com.noahpay.pay.channel.wx.WxRequest;
import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import com.noahpay.pay.channel.wx.model.ExtDataInfo;
import com.noahpay.pay.channel.wx.request.WxPayOrderQueryRequest;
import com.noahpay.pay.channel.wx.request.WxPayUnifiedOrder;
import com.noahpay.pay.channel.wx.response.pay.WxPayOrderQueryResponse;
import com.noahpay.pay.channel.wx.response.pay.WxPayUnifiedorderResponse;
import com.noahpay.pay.commons.db.trade.mapper.PayBillMapper;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.route.constant.ChannelExtParamKey;
import com.noahpay.pay.route.service.ChannelService;
import com.noahpay.pay.trade.bean.model.PayerInfo;
import com.noahpay.pay.trade.constant.TransReturnCode;
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
public class WxPay extends BaseChannelTrans<PayBill> {
    @Resource
    PayBillService payBillService;
    @Resource
    ChannelService channelService;
    @Resource
    PayBillMapper payBillMapper;
    @Value("${server.ip}")
    String ip;

    @Override
    public void channelConvertParam(PayBill bill) {
        bill.setChannelSendSn(bill.getTransId());
        bill.setChannelSendTime(DateUtil.date());
        payBillService.updateSendChannel(bill);
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
        notifyUrl = notifyUrl + "/" + bill.getChannelNo();
        //请求微信下单
        WxPayUnifiedOrder unifiedOrder = new WxPayUnifiedOrder();
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
        unifiedOrder.setNotify_url(notifyUrl);
        unifiedOrder.setTrade_type(bill.getPayType());
        if (StringUtils.isNotBlank(bill.getPayerInfo())) {
            PayerInfo payerInfo = JSON.parseObject(bill.getPayerInfo(), PayerInfo.class);
            unifiedOrder.setOpenid(payerInfo.getSpOpenid());
            unifiedOrder.setSub_openid(payerInfo.getSubOpenid());
        }
//        unifiedOrder.setProduct_id();
//        unifiedOrder.setDevice_info();
//        unifiedOrder.setReceipt();
//        unifiedOrder.setSub_appid();
        unifiedOrder.setAppid(appId);
        unifiedOrder.setMch_id(bill.getChannelMerchantNo());
        unifiedOrder.setSub_mch_id(bill.getChannelSubMerchantNo());
        //发送请求
        WxRequest wxRequest = new WxRequest(unifiedOrder, WxPayUnifiedorderResponse.class, WxPayConstants.UNIFIEDORDER_URL_SUFFIX).setKey(apiKey);
        WxPayUnifiedorderResponse wxResponse = WxClient.execute(wxRequest);
        if (wxResponse == null) {
            //没有获取到下单结果直接当失败
            return payBillService.updateChannelResponse(bill, Response.buildResult(TransReturnCode.FAIL));
        }
        if (wxResponse.isSuccess() && WxPayConstants.SUCCESS.equals(wxResponse.getResultCode())) {
            bill.setChannelCodeUrl(wxResponse.getCodeUrl());
            bill.setChannelPrepayId(wxResponse.getPrepayId());
            bill.setChannelWebUrl(wxResponse.getMwebUrl());
            bill.setPayResultCode(TransReturnCode.SUCCESS.getCode());
            bill.setPayResultNote(TransReturnCode.SUCCESS.getMessage());
            //预下单成功更新信息
            PayBill updatePaybill = new PayBill();
            updatePaybill.setTransId(bill.getTransId());
            updatePaybill.setChannelCodeUrl(bill.getChannelCodeUrl());
            updatePaybill.setChannelPrepayId(bill.getChannelPrepayId());
            updatePaybill.setChannelWebUrl(bill.getChannelWebUrl());
            payBillMapper.updateByUkSelective(updatePaybill);
            return bill;
        } else {
            String channelResultCode = wxResponse.getErrCode();
            if (StringUtils.isBlank(channelResultCode)) {
                channelResultCode = wxResponse.getReturnCode();
            }
            String channelResultNote = wxResponse.getErrCodeDes();
            if (StringUtils.isBlank(channelResultNote)) {
                channelResultNote = wxResponse.getReturnMsg();
            }
            //失败更新数据库
            return payBillService.updateChannelResponse(bill, Response.buildResult(channelResultCode, channelResultNote));
        }
    }

    @Override
    public PayBill channelQuery(PayBill bill) {
        String appId = channelService.getChannelExtParamValue(bill.getChannelNo(), bill.getChannelMerchantNo(), ChannelExtParamKey.APP_ID.code);
        if (StringUtils.isBlank(appId)) {
            throw new BizException(ChannelReturnCode.CODE_6001.formatMessage(ChannelExtParamKey.APP_ID.code));
        }
        String apiKey = channelService.getChannelExtParamValue(bill.getChannelNo(), bill.getChannelMerchantNo(), ChannelExtParamKey.API_KEY.code);
        if (StringUtils.isBlank(apiKey)) {
            throw new BizException(ChannelReturnCode.CODE_6001.formatMessage(ChannelExtParamKey.API_KEY.code));
        }
        //请求微信查询
        WxPayOrderQueryRequest wxPayOrderQuery = new WxPayOrderQueryRequest();
        wxPayOrderQuery.setOut_trade_no(bill.getChannelSendSn());
        wxPayOrderQuery.setTransaction_id(bill.getChannelRecvSn());
        wxPayOrderQuery.setAppid(appId);
        wxPayOrderQuery.setMch_id(bill.getChannelMerchantNo());
        wxPayOrderQuery.setSub_mch_id(bill.getChannelSubMerchantNo());
        //发送请求
        WxRequest wxRequest = new WxRequest(wxPayOrderQuery, WxPayOrderQueryResponse.class, WxPayConstants.ORDERQUERY_URL_SUFFIX).setKey(apiKey);
        WxPayOrderQueryResponse wxResponse = WxClient.execute(wxRequest);
        if (wxResponse == null) {
            return bill;
        }
        if (wxResponse.isSuccess()) {
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
            Response response = null;
            //查询成功解析状态
            if (StringUtils.isNotBlank(wxResponse.getTradeState())) {
                if (WxPayConstants.SUCCESS.equals(wxResponse.getTradeState())) {
                    //明确交易成功
                    response = Response.buildSuccess().setData(channelResponse);
                } else if (!WxPayConstants.NOTPAY.equals(wxResponse.getTradeState())) {
                    //其它状态除了未支付状态的都当失败
                    response = Response.buildResult(wxResponse.getErrCode(), wxResponse.getTradeStateDesc()).setData(channelResponse);
                }
            } else {
                //当失败
                response = Response.buildResult(wxResponse.getErrCode(), wxResponse.getErrCodeDes()).setData(channelResponse);
            }
            //更新支付结果
            return payBillService.updateChannelResponse(bill, response);
        }
        bill.setPayResultNote(wxResponse.getErrCodeDes());
        return bill;
    }
}
