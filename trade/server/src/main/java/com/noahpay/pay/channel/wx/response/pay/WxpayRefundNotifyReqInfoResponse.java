package com.noahpay.pay.channel.wx.response.pay;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * wxpay refund notify req_info response
 */
@Getter
@Setter
public class WxpayRefundNotifyReqInfoResponse implements Serializable {

    private static final long serialVersionUID = 6033075513428576341L;

    /**
     * 微信订单号
     */
    private String transactionId;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 微信退款单号
     */
    private String refundId;
    /**
     * 商户退款单号
     */
    private String outRefundNo;
    /**
     * 订单总金额，单位为分
     */
    private String totalFee;
    /**
     * 应结订单金额，应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额
     */
    private String settlementTotalFee;
    /**
     * 退款总金额,单位为分
     */
    private String refundFee;
    /**
     * 退款金额，退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
     */
    private String settlementRefundFee;
    /**
     * 退款状态 <br/>
     * SUCCESS-退款成功 <br/>
     * CHANGE-退款异常 <br/>
     * REFUNDCLOSE—退款关闭
     */
    private String refundStatus;
    /**
     * 资金退款至用户帐号的时间，格式2017-12-15 09:46:01
     */
    private String successTime;
    /**
     * 退款入账账户<br/>
     * 取当前退款单的退款入账方<br/>
     * 1）退回银行卡：{银行名称}{卡类型}{卡尾号} <br/>
     * 2）退回支付用户零钱：支付用户零钱 <br/>
     * 3）退还商户：商户基本账户、商户结算银行账户<br/>
     * 4）退回支付用户零钱通：支付用户零钱通
     */
    private String refundRecvAccout;
    /**
     * 退款资金来源<br/>
     * REFUND_SOURCE_RECHARGE_FUNDS 可用余额退款/基本账户<br/>
     * REFUND_SOURCE_UNSETTLED_FUNDS 未结算资金退款
     */
    private String refundAccount;
    /**
     * 退款发起来源 <br/>
     * API接口<br/>
     * VENDOR_PLATFORM商户平台
     */
    private String refundRequestSource;

}
