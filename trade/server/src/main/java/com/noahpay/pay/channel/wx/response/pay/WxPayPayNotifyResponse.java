package com.noahpay.pay.channel.wx.response.pay;

import com.noahpay.pay.channel.wx.response.WxPayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay pay notify response
 */
@Getter
@Setter
public class WxPayPayNotifyResponse extends WxPayResponse {

    private static final long serialVersionUID = 3290457041954325683L;

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
     * 子商户公众账号ID
     */
    private String subAppid;
    /**
     * 子商户号
     */
    private String subMchId;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 微信支付分配的终端设备号
     */
    private String deviceInfo;
    /**
     * 签名
     */
    private String sign;
    /**
     * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     */
    private String signType;
    /**
     * 业务结果 SUCCESS/FAIL
     */
    private String resultCode;
    /**
     * 对于业务执行的详细描述
     */
    private String resultMsg;

    /**
     * 错误代码 当result_code为FAIL时返回
     */
    private String errCode;
    /**
     * 错误代码描述 当result_code为FAIL时返回
     */
    private String errCodeDes;
    /**
     * 用户标识
     */
    private String openid;
    /**
     * 用户在子商户appid下的唯一标识
     */
    private String subOpenid;
    /**
     * 用户是否关注公众账号，Y-关注，N-未关注（机构商户不返回）
     */
    private String isSubscribe;
    /**
     * 用户是否关注子公众账号，Y-关注，N-未关注（机构商户不返回）
     */
    private String subIsSubscribe;
    /**
     * 微信订单号
     */
    private String transactionId;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 商家数据包，原样返回
     */
    private String attach;
    /**
     * 银行类型，采用字符串类型的银行标识
     */
    private String bankType;
    /**
     * 订单总金额，单位为分
     */
    private String totalFee;
    /**
     * 应结订单金额，应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额
     */
    private String settlementTotalFee;
    /**
     * 现金支付金额，单位为分
     */
    private String cashFee;
    /**
     * 总代金券金额，代金券金额<=订单金额，订单金额-代金券金额=现金支付金额
     */
    private String couponFee;
    /**
     * 代金券使用数量
     */
    private String couponCount;
    /**
     * 支付完成时间,格式为yyyyMMddHHmmss
     */
    private String timeEnd;

}
