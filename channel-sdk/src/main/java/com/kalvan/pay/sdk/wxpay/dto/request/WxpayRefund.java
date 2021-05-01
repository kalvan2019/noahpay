package com.kalvan.pay.sdk.wxpay.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信申请退款业务请求参数
 */
@Getter
@Setter
public class WxpayRefund implements Serializable {

    private static final long serialVersionUID = 4889384716524540484L;

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

    public WxpayRefund() {
        super();
    }

    /**
     * 商户系统内部订单号退款
     *
     * @param out_trade_no
     * @param out_refund_no
     * @param total_fee
     * @param refund_fee
     * @param refund_desc
     */
    public WxpayRefund(String out_trade_no, String out_refund_no,
                       String total_fee, String refund_fee, String refund_desc) {
        super();
        this.out_trade_no = out_trade_no;
        this.out_refund_no = out_refund_no;
        this.total_fee = total_fee;
        this.refund_fee = refund_fee;
        this.refund_desc = refund_desc;
    }
}
