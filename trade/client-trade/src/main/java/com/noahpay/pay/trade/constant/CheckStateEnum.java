package com.noahpay.pay.trade.constant;

/**
 * 对账状态
 *
 * @author checliang
 */
public enum CheckStateEnum {
    /**
     *
     */
    CHECK_PASS(0, "平账"),
    CHECK_FAIL(1, "不平账"),
    CHECK_WAIT(2, "未对账"),
    ;

    public int code;
    public String desc;

    CheckStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
