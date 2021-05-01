package com.kalvan.pay.sdk.wxpay.api.request.auth;

import com.kalvan.pay.sdk.wxpay.api.WxpayConstants;
import com.kalvan.pay.sdk.wxpay.api.WxpayJsApiRequest;
import com.kalvan.pay.sdk.wxpay.api.response.auth.WxpayJsApiConfigResponse;
import com.kalvan.pay.sdk.wxpay.api.util.WxpayHashMap;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * wxpay jsapi config request
 */
@Getter
@Setter
public class WxpayJsApiConfigRequest implements WxpayJsApiRequest<WxpayJsApiConfigResponse> {

    /**
     * 微信JS-SDK 签名数据
     */
    private String signatureData;

    public String getSignatureData() {
        return signatureData;
    }

    public void setSignatureData(String signatureData) {
        this.signatureData = signatureData;
    }

    @Override
    public Map<String, String> getTextParams() {
        WxpayHashMap txtParams = new WxpayHashMap();
        txtParams.put(WxpayConstants.SIGNATURE_DATA, this.signatureData);
        return txtParams;
    }

    @Override
    public Class<WxpayJsApiConfigResponse> getResponseClass() {
        return WxpayJsApiConfigResponse.class;
    }

}
