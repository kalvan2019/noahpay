package com.noahpay.pay.channel.wx.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信发放代金券业务请求参数
 */
@Getter
@Setter
public class WxPaySendCoupon implements Serializable {
    /**
     * 代金券批次id
     */
    private String coupon_stock_id;
    /**
     * openid记录数（目前支持num=1）
     */
    private String openid_count = "1";
    /**
     * 商户此次发放凭据号（格式：商户id+日期+流水号），商户侧需保持唯一性
     */
    private String partner_trade_no;
    /**
     * 用户openid
     */
    private String openid;
    /**
     * 操作员帐号，默认为商户号，可在商户平台配置操作员对应的api权限
     */
    private String op_user_id;
    /**
     * 微信支付分配的终端设备号
     */
    private String device_info;
}
