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

    public WxPaySendCoupon() {
        super();
    }

    public WxPaySendCoupon(String coupon_stock_id, String partner_trade_no, String openid) {
        super();
        this.coupon_stock_id = coupon_stock_id;
        this.partner_trade_no = partner_trade_no;
        this.openid = openid;
    }


    public WxPaySendCoupon(String coupon_stock_id, String partner_trade_no,
                           String openid, String op_user_id, String device_info) {
        super();
        this.coupon_stock_id = coupon_stock_id;
        this.partner_trade_no = partner_trade_no;
        this.openid = openid;
        this.op_user_id = op_user_id;
        this.device_info = device_info;
    }
}
