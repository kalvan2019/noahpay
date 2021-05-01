package com.noahpay.pay.risk.constant;

/**
 * 风控结果
 *
 * @author chenliang
 */
public enum RiskLevelEnum {
    /**
     *
     */
    RISK_LEVEL_ZERO(0, "阻断"),
    RISK_LEVEL_ONE(1, "放行"),
    RISK_LEVEL_TWO(2, "监控");

    public int code;
    public String desc;

    RiskLevelEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
