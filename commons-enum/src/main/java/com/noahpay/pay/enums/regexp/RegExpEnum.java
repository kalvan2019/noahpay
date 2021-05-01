package com.noahpay.pay.enums.regexp;

/**
 * 正则校验表达式
 *
 * @author chenliang
 */
public interface RegExpEnum {
    /**
     * 手机号码
     */
    String MOBILE_PHONE = "^(1[3|4|5|6|7|8|9][0-9]{9})?$";
    /**
     * 八位数字 一般应用于年月日的日期
     */
    String DATE = "^(\\d{4}\\d{2}\\d{2})?$";
    /**
     * 纯数字
     */
    String IS_NUMBER = "^[0-9]*$";
    /**
     * 身份证
     */
    String ID_CARD = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
    /**
     * 银行账户类型
     */
    String BANK_ACCOUNT_TYPE = "^(0|1|2|3|4)?$";
    /**
     * 协议类型
     */
    String PROTOCOL_TYPE = "^(1|2)?$";
    /**
     * 证件类型
     */
    String CERTIFICATE_TYPE = "^(0|1|2|3|4)?$";
    /**
     * transType类型
     */
    String TRANS_TYPE = "^(0|11|12|13|14|15|16)?$";
    /**
     * 渠道进件商户类型 ，1：个体工商户 2：企业 3：个 人
     */
    String CH_MERCHANT_TYPE = "^(1|2|3)?$";
    /**
     * 渠道进件商户类型 ，1：个体工商户 2：企业
     */
    String REGISTER_MERCHANT_TYPE = "^(1|2)?$";
    /**
     * 渠道进件结算账户类型, 1：对公账户 2：法人账户 3： 被授权人账户
     */
    String CH_ACCOUNT_TYPE = "^(1|2|3)?$";
    /**
     * 结算周期，(1： T+1； 2： D+0; 3:T+0 4:D+1)
     */
    String CH_SETTLE_PERIOD = "^(1|2|3|4)?$";
    /**
     * 进件类型，1：银联服务商商户进件 2：双乾子商户进件
     */
    String CH_REGISTER_TYPE = "^(1|2)?$";
    /**
     * 30-二要素认证;31-三要素认证;32-四要素认证
     */
    String AUT_TYPE = "^(30|31|32)?$";
    /**
     * 扫码类型：1-银联;2-支付宝;3-微信；4-京东
     */
    String QR_CODE_TYPE = "^(1|2|3|4)?$";
}
