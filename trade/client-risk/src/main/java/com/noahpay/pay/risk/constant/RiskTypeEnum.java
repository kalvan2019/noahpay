package com.noahpay.pay.risk.constant;

/**
 * 风控类型定义
 *
 * @author chenliang
 */
public enum RiskTypeEnum {

    /**
     *
     */
    RISK_WITHDRAW_YES(0, "允许提现"),
    RISK_WITHDRAW_NO(1, "不允许提现"),
    BLACK_TYPE_GREY(1, "灰名单"),
    BLACK_TYPE_BLACK(2, "黑名单"),
    RISK_DF_YES(0, "允许代付"),
    RISK_DF_NO(1, "不允许代付");

    public int code;
    public String desc;

    RiskTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
