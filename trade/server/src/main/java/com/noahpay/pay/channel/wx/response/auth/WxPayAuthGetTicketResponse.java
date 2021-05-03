package com.noahpay.pay.channel.wx.response.auth;

import com.noahpay.pay.channel.wx.response.WxPayAuthResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpayauth get ticket resonse
 */
@Getter
@Setter
public class WxPayAuthGetTicketResponse extends WxPayAuthResponse {

    private static final long serialVersionUID = 9115421163416779657L;

    /**
     * 临时票据
     */
    private String ticket;
    /**
     * 有效期7200秒，开发者必须在自己的服务全局缓存
     */
    private String expiresIn;
}
