package com.kalvan.pay.sdk.wxpay.api.request.auth;

import com.kalvan.pay.sdk.wxpay.api.WxpayAuthRequest;
import com.kalvan.pay.sdk.wxpay.api.WxpayConstants;
import com.kalvan.pay.sdk.wxpay.api.response.auth.WxpayAuthGetTicketResponse;
import com.kalvan.pay.sdk.wxpay.api.util.WxpayHashMap;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * wxpayauth get ticket request
 */
@Getter
@Setter
public class WxpayAuthGetTicketRequest implements WxpayAuthRequest<WxpayAuthGetTicketResponse> {

    /**
     * 微信JS接口的临时票据接口
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
    public Class<WxpayAuthGetTicketResponse> getResponseClass() {
        return WxpayAuthGetTicketResponse.class;
    }

}
