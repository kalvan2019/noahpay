package com.kalvan.pay.sdk.wxpay.api.response.pay;

import com.kalvan.pay.sdk.wxpay.api.WxpayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay unifiedorder response
 */
@Getter
@Setter
public class WxpayUnifiedorderResponse extends WxpayResponse {

    private static final long serialVersionUID = 942049939513104128L;

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
     * 设备号
     */
    private String deviceInfo;
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

    /*------------ 以下字段在return_code 和result_code都为SUCCESS的时候有返回 ------------*/

    /**
     * 交易类型
     */
    private String tradeType;
    /**
     * 预支付交易会话标识 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
     */
    private String prepayId;
    /**
     * 二维码链接 trade_type为NATIVE时有返回，用于生成二维码，展示给用户进行扫码支付
     */
    private String codeUrl;
    /**
     * H5支付跳转链接
     */
    private String mwebUrl;

}
