package com.noahpay.pay.channel.wx.response.miniprogram;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信小程序 用户手机号码信息
 *
 * @version 1.3.0
 */
@Getter
@Setter
public class WxMiniProgramPhoneNumber implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 用户绑定的手机号（国外手机号会有区号）
     */
    private String phoneNumber;
    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;
    /**
     * 区号
     */
    private String countryCode;
    /**
     * 水印
     */
    private WxMiniProgramWatermark watermark;

}
