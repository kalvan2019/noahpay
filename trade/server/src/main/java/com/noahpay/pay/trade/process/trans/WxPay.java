package com.noahpay.pay.trade.process.trans;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.JSON;
import com.kalvan.client.constant.CommonStateEnum;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Response;
import com.kalvan.web.util.DateUtil;
import com.noahpay.pay.channel.bean.res.ChannelTransResponse;
import com.noahpay.pay.channel.constant.ChannelReturnCode;
import com.noahpay.pay.channel.wx.WxClient;
import com.noahpay.pay.channel.wx.WxRequest;
import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import com.noahpay.pay.channel.wx.request.WxPayUnifiedOrder;
import com.noahpay.pay.channel.wx.response.pay.WxPayUnifiedorderResponse;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.route.constant.ChannelExtParamKey;
import com.noahpay.pay.route.service.ChannelService;
import com.noahpay.pay.trade.bean.model.SceneInfo;
import com.noahpay.pay.trade.constant.TransReturnCode;
import com.noahpay.pay.trade.process.template.BaseChannelTrans;
import com.noahpay.pay.trade.service.PayBillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        notifyUrl = notifyUrl + "/" + bill.getChannelNo() + "/" + bill.getPayType();
        //请求微信下单
        WxPayUnifiedOrder unifiedOrder = new WxPayUnifiedOrder();
        unifiedOrder.setBody(bill.getDescription());
//        unifiedOrder.setDetail();
        unifiedOrder.setAttach(bill.getAttach());
        unifiedOrder.setOut_trade_no(bill.getChannelSendSn());
        unifiedOrder.setTotal_fee(String.valueOf(bill.getChannelAmount()));
        unifiedOrder.setFee_type(bill.getCurrency());
        SceneInfo sceneInfo = JSON.parseObject(bill.getSceneInfo(), SceneInfo.class);
        unifiedOrder.setSpbill_create_ip(sceneInfo.getDeviceIp());
        if (bill.getTimeStart() != null) {
            unifiedOrder.setTime_start(DatePattern.PURE_DATETIME_FORMAT.format(bill.getTimeStart()));
        }
        if (bill.getTimeExpire() != null) {
            unifiedOrder.setTime_expire(DatePattern.PURE_DATETIME_FORMAT.format(bill.getTimeExpire()));
        }
        unifiedOrder.setNotify_url(notifyUrl);
        unifiedOrder.setTrade_type(bill.getPayType());
//        unifiedOrder.setProduct_id();
//        unifiedOrder.setOpenid();
//        unifiedOrder.setSub_openid();
//        unifiedOrder.setDevice_info();
//        unifiedOrder.setReceipt();
//        unifiedOrder.setSub_appid();
        unifiedOrder.setAppid(appId);
        unifiedOrder.setMch_id(bill.getChannelMerchantNo());
        unifiedOrder.setSub_mch_id(bill.getChannelSubMerchantNo());
        //发送请求
        WxRequest wxRequest = new WxRequest(unifiedOrder, WxPayUnifiedorderResponse.class, WxPayConstants.UNIFIEDORDER_URL_SUFFIX).setKey(apiKey);
        WxPayUnifiedorderResponse wxResponse = WxClient.execute(wxRequest);
        if (wxResponse.isSuccess() && WxPayConstants.SUCCESS.equals(wxResponse.getResultCode())) {
            bill.setChannelCodeUrl(wxResponse.getCodeUrl());
            bill.setChannelPrepayId(wxResponse.getPrepayId());
            bill.setChannelWebUrl(wxResponse.getMwebUrl());
            bill.setPayResultCode(TransReturnCode.SUCCESS.getCode());
            bill.setPayResultNote(TransReturnCode.SUCCESS.getMessage());
            //预下单成功不更新数据库直接返回
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
        ChannelTransResponse channelResponse = new ChannelTransResponse();
        Response response = Response.buildSuccess().setData(channelResponse);
        if (CommonStateEnum.QUERY_FAIL.code == response.getState()) {
            throw new BizException(response);
        }
        return payBillService.updateChannelResponse(bill, response);
    }
}
