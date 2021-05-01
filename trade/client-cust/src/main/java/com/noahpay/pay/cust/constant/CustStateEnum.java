package com.noahpay.pay.cust.constant;

/**
 * 状态
 *
 * @author chenliang
 */
public enum CustStateEnum {
    /**
     * 状态
     */
    VALID(0, "生效"),
    INVALID(1, "未生效"),
    FREEZE(2, "冻结"),
    CANCEL(3, "注销");

    public int code;
    public String desc;

    CustStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
