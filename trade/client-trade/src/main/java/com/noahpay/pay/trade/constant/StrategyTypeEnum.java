package com.noahpay.pay.trade.constant;

/**
 * @author chenliang
 */
public enum StrategyTypeEnum {
    /**
     *
     */
    ONE_WAY(1, "执行一次不管结果"),
    REPEAT(2, "不成功重复执行有次数限制"),
    SUCCESS(3, "不成功重复做必须成功");
    public int code;
    public String desc;

    StrategyTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
