package com.noahpay.pay.channel.wx.enums;

/**
 * js-sdk接口类型枚举
 */
public enum WxpayJsApiType {
    /**
     * 分享接口
     */
    share,
    /**
     * 图像接口
     */
    image,
    /**
     * 音频接口
     */
    audio,
    /**
     * 智能接口（识别音频并返回识别结果接口）
     */
    translate,
    /**
     * 设备信息（获取网络状态接口）
     */
    network,
    /**
     * 地理位置
     */
    location,
    /**
     * 摇一摇周边
     */
    ibeacon,
    /**
     * 界面操作
     */
    ui,
    /**
     * 微信扫一扫
     */
    scan,
    /**
     * 微信小店（跳转微信商品页接口）
     */
    shop,
    /**
     * 微信卡券
     */
    coupon,
    /**
     * 微信支付
     */
    pay,
    /**
     * 快速输入（共享收货地址接口）
     */
    address
}