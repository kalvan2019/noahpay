package com.kalvan.pay.sdk.wxpay.api.response.auth;

import com.kalvan.pay.sdk.wxpay.api.WxpayAuthResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpayauth get code response
 */
@Getter
@Setter
public class WxpayAuthGetCodeResponse extends WxpayAuthResponse {

    private static final long serialVersionUID = 9115421163417979657L;

    /**
     * 获取code的链接
     */
    private String codeUrl;
}
