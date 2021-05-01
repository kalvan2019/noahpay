package com.noahpay.pay.fee.constant;

/**
 * 计费方式
 *
 * @author chenliang
 */
public enum FeeTypeEnum {
    /**
     *
     */
    FIXED(1, "固定收费"),
    RATE(2, "按费率设置"),
    SEGMENT(3, "按金额段设置"),
    ;

    public int code;
    public String desc;

    FeeTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FeeTypeEnum getFeeType(Integer code) {
        if (null == code) {
            return null;
        }
        for (FeeTypeEnum tmpPhoto : FeeTypeEnum.values()) {
            if (tmpPhoto.code == code) {
                return tmpPhoto;
            }
        }
        return null;
    }
}
