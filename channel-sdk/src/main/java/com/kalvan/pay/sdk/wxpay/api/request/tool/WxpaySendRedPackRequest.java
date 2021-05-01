package com.kalvan.pay.sdk.wxpay.api.request.tool;

import com.kalvan.pay.sdk.wxpay.api.WxpayConstants;
import com.kalvan.pay.sdk.wxpay.api.WxpayRequest;
import com.kalvan.pay.sdk.wxpay.api.response.tool.WxpaySendRedPackResponse;
import com.kalvan.pay.sdk.wxpay.api.util.WxpayHashMap;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * wxpay sendredpack request
 */
@Getter
@Setter
public class WxpaySendRedPackRequest implements WxpayRequest<WxpaySendRedPackResponse> {
    /**
     * 发放普通红包接口
     */
    private String bizContent;

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    @Override
    public String getNotifyUrl() {
        return null;
    }

    @Override
    public void setNotifyUrl(String notifyUrl) {
    }

    @Override
    public Map<String, String> getTextParams() {
        WxpayHashMap txtParams = new WxpayHashMap();
        txtParams.put(WxpayConstants.BIZ_CONTENT, this.bizContent);
        return txtParams;
    }

    @Override
    public Class<WxpaySendRedPackResponse> getResponseClass() {
        return WxpaySendRedPackResponse.class;
    }

    @Override
    public boolean isNeedCert() {
        return true;
    }

    @Override
    public String serverDomain() {
        return WxpayConstants.DOMAIN_API_MCH;
    }

    @Override
    public boolean isCheckSign() {
        return false;
    }

}
