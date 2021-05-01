package com.noahpay.pay.trade.constant;

/***
 * @author chenliang
 */
public enum TransTypeEnum {
    /**
     *
     */
    REAL_TIME_COLLECTION(10, "订单收款")
    ;
    public int code;
    public String desc;

    TransTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
