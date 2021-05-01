package com.kalvan.pay.sdk.wxpay.api.response.tool;

import com.kalvan.pay.sdk.wxpay.api.WxpayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay send_coupon response
 */
@Getter
@Setter
public class WxpaySendCouponResponse extends WxpayResponse {

    private static final long serialVersionUID = 7506508026795646550L;

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
     * 返回记录数
     */
    private String respCount;
    /**
     * 成功记录数
     */
    private String successCount;
    /**
     * 失败记录数
     */
    private String failedCount;
    /**
     * 用户在商户appid下的唯一标识
     */
    private String openid;
    /**
     * 对一个用户成功发放代金券则返回代金券id，即ret_code为SUCCESS的时候
     */
    private String couponId;
    /**
     * 返回码，SUCCESS/FAILED
     */
    private String retCode;
    /**
     * 返回信息，当返回码是FAILED的时候填写，否则填空串“”
     */
    private String retMsg;
}
