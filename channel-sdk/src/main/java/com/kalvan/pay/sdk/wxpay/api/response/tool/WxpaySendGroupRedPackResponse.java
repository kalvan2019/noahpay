package com.kalvan.pay.sdk.wxpay.api.response.tool;

import com.kalvan.pay.sdk.wxpay.api.WxpayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay sendgroupredpack response
 */
@Getter
@Setter
public class WxpaySendGroupRedPackResponse extends WxpayResponse {

    private static final long serialVersionUID = 4811040660674770274L;

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

    /*------------ 以下字段在return_code 、result_code都为SUCCESS时有返回 ------------*/

    /**
     * 公众账号ID
     */
    private String wxappid;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户订单号
     */
    private String mchBillno;
    /**
     * 付款金额，单位分
     */
    private String totalAmount;
    /**
     * 红包订单的微信单号
     */
    private String sendListid;
    /**
     * 用户标识
     */
    private String reOpenid;
}
