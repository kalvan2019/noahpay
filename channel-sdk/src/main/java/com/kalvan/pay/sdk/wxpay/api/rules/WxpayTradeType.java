package com.kalvan.pay.sdk.wxpay.api.rules;

/**
 * 交易类型枚举
 */
public enum WxpayTradeType {
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
    private String type;
    /**
     * 使用场景
     */
    private String usageScenario;

    WxpayTradeType(String type, String usageScenario) {
        this.type = type;
        this.usageScenario = usageScenario;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsageScenario() {
        return usageScenario;
    }

    public void setUsageScenario(String usageScenario) {
        this.usageScenario = usageScenario;
    }


}
