package com.noahpay.pay.channel.wx.response.auth;

import com.noahpay.pay.channel.wx.response.WxPayAuthResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpayauth get token resonse
 */
@Getter
@Setter
public class WxPayAuthGetTokenResponse extends WxPayAuthResponse {

    private static final long serialVersionUID = 9115421163417779657L;
    /**
     * 获取到的凭证
     */
    private String accessToken;
    /**
     * 凭证有效时间，单位：秒
     */
    private String expiresIn;

}
