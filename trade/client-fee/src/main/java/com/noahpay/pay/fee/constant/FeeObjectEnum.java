package com.noahpay.pay.fee.constant;

/**
 * 计费对象
 *
 * @author chenliang
 */
public enum FeeObjectEnum {
    /**
     * 状态
     */
    MERCHANT(1, "商户"),
    CUST(2, "客户"),
    PLAT(3,"平台");

    public int code;
    public String desc;

    FeeObjectEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
