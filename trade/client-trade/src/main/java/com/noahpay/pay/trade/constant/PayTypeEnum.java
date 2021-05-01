package com.noahpay.pay.trade.constant;

/**
 * @author chenliang
 */
public enum PayTypeEnum {
    /**
     * 公众号支付,微信浏览器、门店扫码
     */
    WX_JSAPI,
    /**
     * 扫码支付PC网站、门店扫码
     */
    WX_NATIVE,
    /**
     * APP支付第三方APP
     */
    WX_APP,
    /**
     * H5支付第三方手机浏览器
     */
    WX_H5,
    /**
     * 刷卡支付门店刷卡
     */
    WX_MICROPAY;
}