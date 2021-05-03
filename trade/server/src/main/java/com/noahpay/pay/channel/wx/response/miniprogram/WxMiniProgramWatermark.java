package com.noahpay.pay.channel.wx.response.miniprogram;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信小程序 用户水印信息
 *
 * @version 1.3.0
 */
@Getter
@Setter
public class WxMiniProgramWatermark implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 小程序appid
     */
    private String appid;
    /**
     * 时间戳
     */
    private String timestamp;
}
