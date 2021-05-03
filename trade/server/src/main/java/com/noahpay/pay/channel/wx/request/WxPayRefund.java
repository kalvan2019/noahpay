package com.noahpay.pay.channel.wx.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信申请退款业务请求参数
 */
@Getter
@Setter
public class WxPayRefund implements Serializable {
    /**
     * 微信生成的订单号，在支付通知中有返回 （二选一）
     */
    private String transaction_id;
    /**
     * 商户系统内部订单号（二选一）
     */
    private String out_trade_no;
    /**
     * 商户系统内部的退款单号
     */
    private String out_refund_no;
    /**
     * 订单总金额，单位为分
     */
    private String total_fee;
    /**
     * 退款总金额，单位为分
     */
    private String refund_fee;
    /**
     * 退款原因，若商户传入，会在下发给用户的退款消息中体现退款原因
     */
    private String refund_desc;

    /**
     * 通知地址
     */
    private String notify_url;
}
