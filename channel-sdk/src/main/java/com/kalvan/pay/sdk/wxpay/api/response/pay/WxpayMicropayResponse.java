package com.kalvan.pay.sdk.wxpay.api.response.pay;

import com.kalvan.pay.sdk.wxpay.api.WxpayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay micropay response
 */
@Getter
@Setter
public class WxpayMicropayResponse extends WxpayResponse {

    private static final long serialVersionUID = 5822703842125397259L;

    /*------------ 以下字段在return_code为SUCCESS的时候有返回  ------------*/

    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 设备号
     */
    private String deviceInfo;
    /**
     * 签名
     */
    private String sign;
    /**
     * 业务结果 SUCCESS/FAIL
     */
    private String resultCode;
    /**
     * 错误代码 当result_code为FAIL时返回
     */
    private String errCode;
    /**
     * 错误代码描述 当result_code为FAIL时返回
     */
    private String errCodeDes;

    /*------------ 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回 ------------*/

    /**
     * 用户标识
     */
    private String openid;
    /**
     * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     */
    private String isSubscribe;
    /**
     * 交易类型
     */
    private String tradeType;
    /**
     * 付款银行 银行类型，采用字符串类型的银行标识
     */
    private String bankType;
    /**
     * 订单总金额，单位为分
     */
    private String totalFee;
    /**
     * 应结订单金额 当订单使用了免充值型优惠券后返回该参数，应结订单金额=订单金额-免充值优惠券金额。
     */
    private String settlementTotalFee;
    /**
     * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
     */
    private String feeType;
    /**
     * 现金支付金额订单现金支付金额
     */
    private String cashFee;
    /**
     * 代金券金额  代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额
     */
    private String couponFee;
    /**
     * 代金券使用数量
     */
    private String couponCount;
    /**
     * 微信支付订单号
     */
    private String transactionId;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    private String outTradeNo;
    /**
     * 附加数据，原样返回
     */
    private String attach;
    /**
     * 支付完成时间，格式为yyyyMMddHHmmss
     */
    private String timeEnd;
    /**
     * 营销详情
     */
    private String promotionDetail;
}
