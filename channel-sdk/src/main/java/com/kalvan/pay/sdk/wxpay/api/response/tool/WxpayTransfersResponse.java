package com.kalvan.pay.sdk.wxpay.api.response.tool;

import com.kalvan.pay.sdk.wxpay.api.WxpayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay transfer response
 */
@Getter
@Setter
public class WxpayTransfersResponse extends WxpayResponse {

    private static final long serialVersionUID = 4881040660674770274L;

    /*------------ 以下字段在return_code为SUCCESS的时候有返回  ------------*/

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
     * 申请商户号的appid或商户号绑定的appid（企业号corpid即为此appId）
     */
    private String mchAppid;
    /**
     * 微信支付分配的商户号
     */
    private String mchid;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 微信支付分配的终端设备号
     */
    private String deviceInfo;

    /*------------ 以下字段在return_code 、result_code都为SUCCESS时有返回 ------------*/

    /**
     * 商户单号
     */
    private String partnerTradeNo;
    /**
     * 企业付款成功，返回的微信付款单号
     */
    private String paymentNo;
    /**
     * 企业付款成功时间
     */
    private String paymentTime;
}
