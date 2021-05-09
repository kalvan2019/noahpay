package com.noahpay.pay.channel.wx;

import cn.hutool.core.bean.BeanUtil;
import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import com.noahpay.pay.channel.wx.response.WxPayResponse;
import com.noahpay.pay.channel.wx.util.WebUtils;
import com.noahpay.pay.channel.wx.util.WxpayHashMap;
import com.noahpay.pay.channel.wx.util.WxpaySignature;
import com.noahpay.pay.channel.wx.util.WxpayUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 微信客户端工具
 *
 * @author chenliang
 */
@Slf4j
public class WxClient {

    /**
     * 调用微信api接口
     */
    public static <T extends WxPayResponse> T execute(WxRequest<?> request) {
        String requestUrl = request.getServerDomain() + request.getPath();
        WxMsgInfo msg = request.getMsg();
        msg.setNonce_str(WxpayUtils.generateNonceStr());
        try {
            String sign = WxpaySignature.generateSignature(BeanUtil.beanToMap(msg, false, true), request.getKey(), msg.getSign_type());
            msg.setSign(sign);
        } catch (Exception e) {
            log.error("failed to generate signature", e);
            return null;
        }
        Map<String, Object> requestMap = BeanUtil.beanToMap(msg, false, true);
        String requestXml = WxpayUtils.mapToXml(requestMap);
        if (StringUtils.isBlank(requestXml)) {
            return null;
        }
        String result = "";
        try {
            log.debug("[wxClient] request params: {}", requestXml);
            if (request.isNeedCert()) {
                result = WebUtils.requestWithCert(request.getServerDomain(), request.getPath(), requestXml, request.getConnectTimeout(), request.getReadTimeout(), msg.getMch_id(), request.getCertFile());
            } else {
                result = WebUtils.requestWithoutCert(request.getServerDomain(), request.getPath(), requestXml, request.getConnectTimeout(), request.getReadTimeout());
            }
            log.debug("[wxClient] response result: {}", result);
        } catch (Exception e) {
            log.error(requestUrl + "the request failed", e);
        }
        WxpayHashMap resultMap = new WxpayHashMap();
        if (StringUtils.isNotBlank(result)) {
            try {
                if (result.indexOf(WxPayConstants.LEFT_ANGLE_BRACKETS) == 0) {
                    resultMap = WxpayUtils.xmlToMap(result);
                } else {
                    // 对账单下载
                    resultMap.put(WxPayConstants.RETURN_CODE, WxPayConstants.SUCCESS);
                    resultMap.put(WxPayConstants.RETURN_MSG, WxPayConstants.OK);
                    resultMap.put(WxPayConstants.DATA, result);
                }
                T tRsp = WxpayUtils.mapToObject(resultMap, request.getResponseClass());
                tRsp.setParams(requestMap);
                return tRsp;
            } catch (Exception e) {
                log.error("an unexpected error", e);
            }
        }
        return null;
    }

}
