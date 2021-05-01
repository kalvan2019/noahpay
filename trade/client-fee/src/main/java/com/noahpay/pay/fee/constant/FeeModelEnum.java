package com.noahpay.pay.fee.constant;

/**
 * 计费模式
 *
 * @author chenliang
 */
public enum FeeModelEnum {
    /**
     * 状态
     */
    REAL(1, "实时计费"),
    DAY(2, "日结计费"),
    MOTH(3, "月结计费");

    public int code;
    public String desc;

    FeeModelEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
