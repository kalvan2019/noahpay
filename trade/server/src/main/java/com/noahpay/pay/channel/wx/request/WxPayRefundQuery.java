package com.noahpay.pay.channel.wx.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信退款查询业务请求参数
 */
@Getter
@Setter
public class WxPayRefundQuery implements Serializable {
    /**
     * 微信订单号（四选一）
     */
    private String transaction_id;
    /**
     * 商户系统内部订单号（四选一）
     */
    private String out_trade_no;
    /**
     * 商户系统内部的退款单号（四选一）
     */
    private String out_refund_no;
    /**
     * 微信生成的退款单号，在申请退款接口有返回（四选一）
     */
    private String refund_id;
    /**
     * 偏移量，当部分退款次数超过10次时可使用，表示返回的查询结果从这个偏移量开始取记录
     */
    private String offset;
}
