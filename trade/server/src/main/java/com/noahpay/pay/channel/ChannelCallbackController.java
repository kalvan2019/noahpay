package com.noahpay.pay.channel;

import com.alibaba.fastjson.JSON;
import com.kalvan.client.constant.CommonStateEnum;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Request;
import com.noahpay.pay.channel.constant.ChannelReturnCode;
import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import com.noahpay.pay.channel.wx.model.ExtDataInfo;
import com.noahpay.pay.channel.wx.response.pay.WxPayPayNotifyResponse;
import com.noahpay.pay.channel.wx.util.StreamUtils;
import com.noahpay.pay.channel.wx.util.WxpayHashMap;
import com.noahpay.pay.channel.wx.util.WxpaySignature;
import com.noahpay.pay.channel.wx.util.WxpayUtils;
import com.noahpay.pay.route.constant.ChannelExtParamKey;
import com.noahpay.pay.route.service.ChannelService;
import com.noahpay.pay.trade.bean.req.CallBackRequest;
import com.noahpay.pay.trade.controller.NotifyController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * @author chenliang
 */
@Controller
@Slf4j
public class ChannelCallbackController {
    public static final String FAILD = "faild";
    @Resource
    ChannelService channelService;
    String responseSuccess = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";

    @Resource
    NotifyController notifyController;

    /**
     * 微信支付结果通知
     *
     * @param request 请求参数
     * @return 处理结果
     */
    @RequestMapping("callback/wx/{channelNo}")
    public String wxCallback(@PathVariable(value = "channelNo") Integer channelNo, HttpServletRequest request) {
        WxPayPayNotifyResponse wxResponse;
        try {
            ServletInputStream in = request.getInputStream();
            String xmlStr = StreamUtils.readText(in, WxPayConstants.CHARSET_UTF8);
            log.info("接收微信支付结果通知");
            WxpayHashMap notifyMap = WxpayUtils.xmlToMap(xmlStr);
            wxResponse = WxpayUtils.mapToObject(notifyMap, WxPayPayNotifyResponse.class);
            String apiKey = channelService.getChannelExtParamValue(channelNo, wxResponse.getMchId(), ChannelExtParamKey.API_KEY.code);
            if (StringUtils.isBlank(apiKey)) {
                throw new BizException(ChannelReturnCode.CODE_6001.formatMessage(ChannelExtParamKey.API_KEY.code));
            }
            boolean check = WxpaySignature.isSignatureValid(notifyMap, apiKey, wxResponse.getSignType());
            if (!check) {
                log.error("验证签名失败");
                return FAILD;
            }
        } catch (Exception e) {
            log.error("微信通知处理异常", e);
            return FAILD;
        }
        CallBackRequest callBackRequest = new CallBackRequest();
        callBackRequest.setChannelNo(channelNo);
        callBackRequest.setChannelSendSn(wxResponse.getOutTradeNo());
        callBackRequest.setChannelRecvSn(wxResponse.getTransactionId());
        if (StringUtils.isNotBlank(wxResponse.getTotalFee())) {
            callBackRequest.setChannelRecvAmount(Long.parseLong(wxResponse.getTotalFee()));
        }
        ExtDataInfo extDataInfo = new ExtDataInfo();
        extDataInfo.setOpenid(wxResponse.getOpenid());
        extDataInfo.setIsSubscribe(wxResponse.getIsSubscribe());
        extDataInfo.setSubOpenid(wxResponse.getSubOpenid());
        extDataInfo.setSubIsSubscribe(wxResponse.getSubIsSubscribe());
        callBackRequest.setChannelRecvExt(JSON.toJSONString(extDataInfo));
        if (wxResponse.isSuccess() && WxPayConstants.SUCCESS.equals(wxResponse.getResultCode())) {
            callBackRequest.setState(CommonStateEnum.SUCCESS.code);
            callBackRequest.setCode(wxResponse.getResultCode());
            callBackRequest.setMessage(wxResponse.getResultMsg());
        } else {
            log.error("通知状态非成功");
            return FAILD;
        }
        notifyController.callback(new Request<>().setData(callBackRequest));
        return responseSuccess;
    }
}