package com.noahpay.pay.channel.wx;

import com.noahpay.pay.channel.wx.enums.WxpaySignType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenliang
 */
@Getter
@Setter
public class WxMsgInfo implements Serializable {
    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 子商户公众账号ID
     */
    private String sub_appid;
    /**
     * 子商户号
     */
    private String sub_mch_id;
    /**
     * 随机字符串
     * 统一处理
     */
    private String nonce_str;
    /**
     * 签名
     * 统一处理
     */
    private String sign;
    /**
     * 签名算法
     * 默认
     */
    private String sign_type = WxpaySignType.MD5.name();
}
