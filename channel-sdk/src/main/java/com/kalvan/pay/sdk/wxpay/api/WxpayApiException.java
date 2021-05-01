package com.kalvan.pay.sdk.wxpay.api;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信业务异常类
 */
@Getter
@Setter
public class WxpayApiException extends Exception {

    private static final long serialVersionUID = 5144757309644981452L;

    public WxpayApiException() {
        super();
    }

    public WxpayApiException(String message, Throwable cause,
                             boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WxpayApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public WxpayApiException(String message) {
        super(message);
    }

    public WxpayApiException(Throwable cause) {
        super(cause);
    }

}
