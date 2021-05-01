package com.kalvan.pay.sdk.wxpay.api.response.tool;

import com.kalvan.pay.sdk.wxpay.api.WxpayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay shorturl response
 */
@Getter
@Setter
public class WxpayShortUrlResponse extends WxpayResponse {

    private static final long serialVersionUID = 942048939513104128L;

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
     * 签名
     */
    private String sign;
    /**
     * 业务结果，SUCCESS/FAIL
     */
    private String resultCode;
    /**
     * 错误代码 <br/>
     * SYSTEMERROR--系统错误 <br/>
     * URLFORMATERROR—URL格式错误
     */
    private String errCode;
    /**
     * 错误代码描述
     */
    private String errCodeDes;

    /**
     * 转换后的URL
     */
    private String shortUrl;
}
