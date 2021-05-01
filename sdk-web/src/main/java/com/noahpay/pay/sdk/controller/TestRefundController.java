package com.noahpay.pay.sdk.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.noahpay.pay.sdk.bean.req.RefundRequest;
import com.noahpay.pay.ApiServer;
import com.noahpay.pay.bean.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenliang
 */
@Slf4j
@Controller
@RequestMapping("refund")
public class TestRefundController {
    /**
     * 订单退款
     */
    public static final String SERVICE_REFUND = "refund";

    @Value("${sdk.web.url}")
    String sdkWebUrl;

    /**
     * 退款
     */
    @RequestMapping(value = "")
    public String refund(RefundRequest refundRequest, HttpServletRequest request) throws Exception {
        String appId = ApiServer.APP_SDK.getSdkInfo().getAppCertInfoMap().keySet().iterator().next();
        if (StringUtils.isEmpty(refundRequest.getAppId())) {
            refundRequest.setAppId(appId);
        }
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("orderId", RandomUtil.randomInt(20) + "");
        paramsMap.put("orgOrderId", refundRequest.getOrderId());
        paramsMap.put("orgTransId", refundRequest.getTransId());
        paramsMap.put("refund_amount", refundRequest.getAmount());
        paramsMap.put("refund_reason", "自动退款");
        paramsMap.put("notifyPgUrl", sdkWebUrl + "/refund/notify/pg");
        //使用sdk加密和签名再转发请求
        String result = ApiServer.post(SERVICE_REFUND, refundRequest.getAppId(), JSONUtil.toJsonStr(paramsMap));
        if (!result.contains(refundRequest.getTransId())) {
            return "fail";
        }
        return "refund_success";
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
}
