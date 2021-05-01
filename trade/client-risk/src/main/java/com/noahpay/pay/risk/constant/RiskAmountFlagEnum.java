package com.noahpay.pay.risk.constant;

/**
 * 风控账户资金进出
 *
 * @author chenliang
 */
public enum RiskAmountFlagEnum {

    /**
     *
     */
    AMOUNT_IN_FLAG(0, "进账"),
    AMOUNT_OUT_FLAG(1, "出账");


    public int code;
    public String desc;

    RiskAmountFlagEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
