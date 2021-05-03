package com.noahpay.pay.channel.wx.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信交易保障业务请求参数
 */
@Getter
@Setter
public class WxPayReport implements Serializable {
    /**
     * 微信支付分配的终端设备号，商户自定义
     */
    private String device_info;
    /**
     * 接口URL，刷卡支付终端上报统一填：https://api.mch.weixin.qq.com/pay/batchreport/micropay/total
     */
    private String interface_url;
    /**
     * 发起接口调用时的机器IP
     */
    private String user_ip;
    /**
     * 上报数据包
     */
    private String trades;
}
