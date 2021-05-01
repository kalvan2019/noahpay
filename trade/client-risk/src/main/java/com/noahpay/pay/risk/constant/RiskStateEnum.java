package com.noahpay.pay.risk.constant;

/**
 * 状态
 *
 * @author chenliang
 */
public enum RiskStateEnum {

    /**
     *
     */
    VALID(0, "生效"),
    INVALID(1, "未生效"),
    ;

    public int code;
    public String desc;

    RiskStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
