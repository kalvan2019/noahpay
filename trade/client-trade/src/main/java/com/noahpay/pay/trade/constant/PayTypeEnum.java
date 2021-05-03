package com.noahpay.pay.trade.constant;

/**
 * @author chenliang
 */
public enum PayTypeEnum {
    /**
     * 公众号支付
     */
    JSAPI("JSAPI", "微信浏览器、门店扫码"),
    /**
     * 扫码支付
     */
    NATIVE("NATIVE", "PC网站、门店扫码"),
    /**
     * APP支付
     */
    APP("APP", "第三方APP"),
    /**
     * H5支付
     */
    MWEB("MWEB", "第三方手机浏览器"),
    /**
     * 刷卡支付
     */
    MICROPAY("MICROPAY", "门店刷卡");

    /**
     * 类型
     */
    public String code;
    /**
     * 使用场景
     */
    public String desc;

    PayTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}