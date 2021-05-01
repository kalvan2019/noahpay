package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelRefundQueryRequest extends ChannelBaseRequest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 原退款发往银行流水(查询退款交易时可能用到)
     */
    private String orgRefundChannelSendSn;
    /**
     * 原退款银行后台流水(查询退款交易时可能用到)
     */
    private String orgRefundChannelRecvSn;
    /**
     * 原退款交易通道返回会计日期
     */
    private Integer orgRefundChannelAccountDate;
    /**
     * 原交易退款金額
     */
    private Long orgRefundAmount;
}
