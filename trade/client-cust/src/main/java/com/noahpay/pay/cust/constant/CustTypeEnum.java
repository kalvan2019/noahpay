package com.noahpay.pay.cust.constant;

/**
 * 客户类型
 *
 * @author chenliang
 */
public enum CustTypeEnum {
    /**
     *
     */
    CUST("00", "直营客户"),
    PARTNER("01", "合作方"),
    MERCHANT("02", "直联商户"),
    SUB_MERCHANT("03", "子商户");

    public String code;
    public String desc;

    CustTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
