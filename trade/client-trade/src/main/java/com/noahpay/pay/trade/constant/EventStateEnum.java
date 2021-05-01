package com.noahpay.pay.trade.constant;

/**
 * @author chenliang
 */
public enum EventStateEnum {
    /**
     *
     */
    SUCCESS(0, "处理成功"),
    FAIL(1, "处理失败"),
    WAIT(2, "待处理"),
    ;
    public int code;
    public String desc;

    EventStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
