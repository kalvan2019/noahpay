package com.noahpay.pay.channel.wx.request;

import com.noahpay.pay.channel.wx.WxMsgInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信订单查询
 */
@Getter
@Setter
public class WxPayOrderQueryRequest extends WxMsgInfo {
    /**
     * 微信支付订单号
     */
    private String transaction_id;
    /**
     * 商户订单号
     */
    private String out_trade_no;
}
