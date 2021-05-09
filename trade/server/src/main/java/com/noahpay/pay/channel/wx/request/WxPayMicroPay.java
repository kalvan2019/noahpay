package com.noahpay.pay.channel.wx.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信付款码支付业务请求参数
 */
@Getter
@Setter
public class WxPayMicroPay extends WxPayUnifiedOrder {

    /**
     * 扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     * （注：用户付款码条形码规则：18位纯数字，以10、11、12、13、14、15开头）
     */
    private String auth_code;
}
