package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 向通道发送退款交易传入参数
 *
 * @author chenliang
 */
@Setter
@Getter
public class ChannelRefundRequest extends ChannelTransRequest
        implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 原交易金额
     */
    private Long orgAmount;
    /**
     * 原交易发往银行流水
     */
    private String orgChannelSendSn;
    /**
     * 原交易银行返回流水
     */
    private String orgChannelRecvSn;
    /**
     * 原交易发往银行时间
     */
    private Integer orgTradeDate;
    /**
     * 退款原因
     */
    private String refundReason;
}
