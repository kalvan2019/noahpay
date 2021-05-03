package com.noahpay.pay.channel.wx.response.miniprogram;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信小程序 用户信息
 *
 * @version 1.3.0
 */
@Getter
@Setter
public class WxMiniProgramUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String openId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户性别 0：未知、1：男、2：女
     */
    private Integer gender;
    /**
     * 用户所在城市
     */
    private String city;
    /**
     * 用户所在省份
     */
    private String province;
    /**
     * 用户所在国家
     */
    private String country;
    /**
     * 用户头像图片的 URL
     */
    private String avatarUrl;
    /**
     * unionId
     */
    private String unionId;
    /**
     * 水印
     */
    private WxMiniProgramWatermark watermark;

}
