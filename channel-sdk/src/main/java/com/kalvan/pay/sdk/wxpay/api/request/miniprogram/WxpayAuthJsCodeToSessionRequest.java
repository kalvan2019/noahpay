package com.kalvan.pay.sdk.wxpay.api.request.miniprogram;

import com.kalvan.pay.sdk.wxpay.api.WxpayAuthRequest;
import com.kalvan.pay.sdk.wxpay.api.WxpayConstants;
import com.kalvan.pay.sdk.wxpay.api.response.miniprogram.WxpayAuthJsCodeToSessionResponse;
import com.kalvan.pay.sdk.wxpay.api.util.WxpayHashMap;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * wxpay miniprogram auth jscode2session request
 *
 * @version 1.3.0
 */
@Getter
@Setter
public class WxpayAuthJsCodeToSessionRequest implements WxpayAuthRequest<WxpayAuthJsCodeToSessionResponse> {

    /**
     * 小程序登录凭证校验接口
     */
    private String bizContent;

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    @Override
    public Map<String, String> getTextParams() {
        WxpayHashMap txtParams = new WxpayHashMap();
        txtParams.put(WxpayConstants.BIZ_CONTENT, this.bizContent);
        return txtParams;
    }

    @Override
    public Class<WxpayAuthJsCodeToSessionResponse> getResponseClass() {
        return WxpayAuthJsCodeToSessionResponse.class;
    }

    @Override
    public String serverDomain() {
        return WxpayConstants.DOMAIN_API;
    }

}
