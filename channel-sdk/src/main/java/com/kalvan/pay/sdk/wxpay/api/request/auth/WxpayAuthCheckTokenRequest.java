package com.kalvan.pay.sdk.wxpay.api.request.auth;

import com.kalvan.pay.sdk.wxpay.api.WxpayAuthRequest;
import com.kalvan.pay.sdk.wxpay.api.WxpayConstants;
import com.kalvan.pay.sdk.wxpay.api.response.auth.WxpayAuthCheckTokenResponse;
import com.kalvan.pay.sdk.wxpay.api.util.WxpayHashMap;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * wxpayauth check token request
 */
@Getter
@Setter
public class WxpayAuthCheckTokenRequest implements WxpayAuthRequest<WxpayAuthCheckTokenResponse> {

    /**
     * 检验授权凭证（access_token）是否有效接口
     */
    private String bizContent;

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    @Override
    public String serverDomain() {
        return WxpayConstants.DOMAIN_API;
    }

    @Override
    public Map<String, String> getTextParams() {
        WxpayHashMap txtParams = new WxpayHashMap();
        txtParams.put(WxpayConstants.BIZ_CONTENT, this.bizContent);
        return txtParams;
    }

    @Override
    public Class<WxpayAuthCheckTokenResponse> getResponseClass() {
        return WxpayAuthCheckTokenResponse.class;
    }

}
