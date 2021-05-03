package com.noahpay.pay.channel.wx.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信业务异常类
 *
 * @author chenliang
 */
@Getter
@Setter
public class WxPayApiException extends Exception {

    public WxPayApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
