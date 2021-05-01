package com.noahpay.pay.sdk.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.noahpay.pay.ApiServer;
import com.noahpay.pay.bean.ApiModel;
import com.noahpay.pay.sdk.bean.req.GatewayPayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenliang
 */
@Slf4j
@Controller
@RequestMapping("pay")
public class TestPayController {
    /**
     * 订单收银台
     */
    public static final String SERVICE_CASHIER = "cashier/order";
    public static final String SERVICE_CALLBACK = "cashier/callback";

    @Value("${sdk.web.url}")
    String sdkWebUrl;

    /**
     * 网关支付
     */
    @RequestMapping(value = "")
    public String send(GatewayPayRequest gatewayPayRequest, Model model) {
        String appId = ApiServer.APP_SDK.getSdkInfo().getAppCertInfoMap().keySet().iterator().next();
        if (StringUtils.isEmpty(gatewayPayRequest.getAppId())) {
            gatewayPayRequest.setAppId(appId);
        }
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("orderId", DatePattern.PURE_DATE_FORMAT.format(new Date()) + RandomUtil.randomInt(100000, 999999));
        paramsMap.put("orderDate", DatePattern.PURE_DATE_FORMAT.format(new Date()));
        paramsMap.put("orderAmount", RandomUtil.randomInt(10, 500) + "");
        paramsMap.put("orderFee", "");
        paramsMap.put("orderNote", "支付体验");
        paramsMap.put("orderExpiryTime", DateUtil.format(DateUtil.offset(new Date(), DateField.MINUTE, 30), "yyyy-MM-dd HH:mm:ss"));
        paramsMap.put("bankAccountNo", "");
        paramsMap.put("bankAccountName", "");
        paramsMap.put("mobile", "");
        paramsMap.put("certificateNo", "");
        paramsMap.put("notifyBgUrl", sdkWebUrl+"/pay/notify/bg");
        paramsMap.put("notifyPgUrl", sdkWebUrl+"/pay/notify/pg");
        paramsMap.put("payType", gatewayPayRequest.getPayType());
        paramsMap.put("payMode", gatewayPayRequest.getPayMode());
        paramsMap.put("bankType", gatewayPayRequest.getBankType());

        ApiModel apiModel = ApiServer.APP_SDK.encryptAndSign(gatewayPayRequest.getAppId(), JSONUtil.toJsonStr(paramsMap));
        model.addAttribute("actionUrl", ApiServer.APP_SDK.getSdkInfo().getUrl() + "/" + SERVICE_CASHIER);
        model.addAttribute("params", BeanUtil.beanToMap(apiModel));
        return "pay_jump";
    }

    /**
     * 后台回调接口
     * 模拟下游接收异步通知并返回成功标识
     *
     * @param apiModel 通知参数
     * @return 接收成功返回标识避免重发 0000
     */
    @RequestMapping(value = "notify/bg", method = {RequestMethod.POST, RequestMethod.GET})
    public String notifyBg(@RequestBody ApiModel apiModel) {
        log.info("上游异步回调通知参数为：{}", JSONUtil.toJsonStr(apiModel));
        apiModel = ApiServer.APP_SDK.decryptAndVerify(apiModel);
        log.info("解密数据：{}", JSONUtil.toJsonStr(apiModel));
        log.info("异步通知接收成功返回成功握手报文0000");
        return "0000";
    }

    /**
     * 页面回调接口
     * 模拟下游接收异步通知并返回成功标识
     */
    @RequestMapping(value = "notify/pg", method = {RequestMethod.POST, RequestMethod.GET})
    public String notifyPg(HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, String[]> result = request.getParameterMap();
        Map<String, String> resultMap = TestController.parseNotifyMsg(result);
        log.info("上游异步页面回调通知参数为：{}", resultMap);
        ApiModel apiModel = ApiServer.APP_SDK.decryptAndVerify(BeanUtil.mapToBean(resultMap, ApiModel.class, true, null));
        log.info("解密数据：{}", JSONUtil.toJsonStr(apiModel));
        //TODO
        model.addAttribute("returnMsg", "订单支付成功");
        model.addAttribute("refundFlag", "sdkConfig.getPay().isAutoRefund()");
        model.addAttribute("orderId", "notifyPay.getOut_trade_no()");
        model.addAttribute("transId", "notifyPay.getTrade_no()");
        model.addAttribute("amount", "notifyPay.getTotal_amount()");
        model.addAttribute("actionUrl", "");
        return "pay_success";
        // TODO
//        return "failed";
    }

    /**
     * 模拟银行付款页面
     * 模拟跳转到银行之后的页面
     */
    @RequestMapping(value = "test/bank", method = {RequestMethod.POST, RequestMethod.GET})
    public String testBank(String channelNo, String channelSendSn, ModelMap model) {
        //TODO 模拟银行付款完成页面回调到channel->trade->收银台
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("channelNo", channelNo);
        paramsMap.put("channelSendSn", channelSendSn);
        paramsMap.put("channelRecvSn", "channelRecvSn");
        paramsMap.put("sate", 0);
        paramsMap.put("channelCode","0000");
        paramsMap.put("channelDesc","处理成功");
        model.addAttribute("actionUrl", ApiServer.APP_SDK.getSdkInfo().getUrl() + "/" + SERVICE_CALLBACK);
        model.addAttribute("params", paramsMap);
        return "test_bank";
    }
}
