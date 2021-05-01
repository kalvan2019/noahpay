package com.noahpay.pay.trade.constant;

/**
 * @author chenliang
 */
public enum BankAccountTypeEnum {
    /**
     *
     */
    ALL(0, "所有"),
    DEBIT(1, "借记卡"),
    CREDIT(2, "贷记卡"),
    ;
    public int code;
    public String desc;

    BankAccountTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}