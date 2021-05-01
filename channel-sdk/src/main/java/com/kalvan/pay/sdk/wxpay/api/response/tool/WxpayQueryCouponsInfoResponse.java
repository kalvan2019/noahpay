package com.kalvan.pay.sdk.wxpay.api.response.tool;

import com.kalvan.pay.sdk.wxpay.api.WxpayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay querycouponsinfo response
 */
@Getter
@Setter
public class WxpayQueryCouponsInfoResponse extends WxpayResponse {

    private static final long serialVersionUID = 3909854473111640595L;

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
     * 微信支付分配的子商户号，受理模式下必填
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
    /**
     * 代金券批次id
     */
    private String couponStockId;
    /**
     * 代金券名称
     */
    private String couponName;
    /**
     * 代金券面值,单位是分
     */
    private String couponValue;
    /**
     * 代金券使用最低限额,单位是分
     */
    private String couponMininumn;
    /**
     * 代金券状态：SENDED-可用，USED-已实扣，EXPIRED-已过期
     */
    private String couponStatus;
    /**
     * 代金券描述
     */
    private String couponDesc;
    /**
     * 代金券实际使用金额
     */
    private String couponUseValue;
    /**
     * 代金券剩余金额：部分使用情况下，可能会存在券剩余金额
     */
    private String couponRemainValue;
    /**
     * 代金券发放来源：FULL_SEND-满送 NORMAL-普通发放场景
     */
    private String sendSource;
    /**
     * 该代金券是否允许部分使用标识：1-表示支持部分使用
     */
    private String isPartialUse;

}
