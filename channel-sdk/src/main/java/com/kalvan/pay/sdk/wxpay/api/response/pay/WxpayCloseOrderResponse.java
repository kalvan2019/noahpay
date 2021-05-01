package com.kalvan.pay.sdk.wxpay.api.response.pay;

import com.kalvan.pay.sdk.wxpay.api.WxpayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay order query response
 */
@Getter
@Setter
public class WxpayCloseOrderResponse extends WxpayResponse {

    private static final long serialVersionUID = 1822703842125393259L;

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
}
