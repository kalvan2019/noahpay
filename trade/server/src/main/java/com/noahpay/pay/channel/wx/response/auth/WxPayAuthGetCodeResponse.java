package com.noahpay.pay.channel.wx.response.auth;

import com.noahpay.pay.channel.wx.response.WxPayAuthResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpayauth get code response
 */
@Getter
@Setter
public class WxPayAuthGetCodeResponse extends WxPayAuthResponse {

    private static final long serialVersionUID = 9115421163417979657L;

    /**
     * 获取code的链接
     */
    private String codeUrl;
}
