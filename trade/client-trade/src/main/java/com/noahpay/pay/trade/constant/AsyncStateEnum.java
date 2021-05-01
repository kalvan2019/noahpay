package com.noahpay.pay.trade.constant;

/**
 * @author chenliang
 */
public enum AsyncStateEnum {
    /**
     *
     */
    SUCCESS(0, "处理成功"),
    WAIT(1, "待处理"),
    PROCESS(2, "处理中"),
    OVERTIME(3, "处理超时"),
    FAIL(4, "处理失败"),
    CANCEL(5, "人工取消");
    public int code;
    public String desc;

    AsyncStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
