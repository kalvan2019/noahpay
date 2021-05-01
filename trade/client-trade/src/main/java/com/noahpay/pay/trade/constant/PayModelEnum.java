package com.noahpay.pay.trade.constant;

/**
 * @author chenliang
 */
public enum PayModelEnum {
    /**
     *
     */
    CASHIER(1, "收银台模式"),
    DIRECT(2, "直联模式"),
    ;
    public int code;
    public String desc;

    PayModelEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}