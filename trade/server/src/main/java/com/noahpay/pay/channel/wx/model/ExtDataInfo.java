package com.noahpay.pay.channel.wx.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信扩展数据
 *
 * @author chenliang
 */
@Setter
@Getter
public class ExtDataInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户标识
     */
    private String openid;
    /**
     * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     */
    private String isSubscribe;
    /**
     * 用户在子商户appid下的唯一标识
     */
    private String subOpenid;
    /**
     * 用户在子商户appid下的唯一标识
     */
    private String subIsSubscribe;
}
