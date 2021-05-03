package com.noahpay.pay.channel.wx.response.pay;

import com.noahpay.pay.channel.wx.response.WxPayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay refund notify response
 */
@Getter
@Setter
public class WxPayRefundNotifyResponse extends WxPayResponse {

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
     * 特约商户公众账号ID
     */
    private String subAppid;
    /**
     * 特约商户商户号
     */
    private String subMchId;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 加密信息请用商户秘钥进行解密
     */
    private String reqInfo;

}
