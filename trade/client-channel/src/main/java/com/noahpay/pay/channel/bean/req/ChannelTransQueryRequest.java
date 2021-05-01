package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelTransQueryRequest extends ChannelBaseRequest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 原订单发往银行流水
     */
    private String orgChannelSendSn;
    /**
     * 原订单银行后台流水
     */
    private String orgChannelRecvSn;
    /**
     * 原交易金额,有些通道需要
     */
    private Long orgAmount;
    /**
     * 原交易通道会计日期
     */
    private Integer orgChannelAccountDate;
    /**
     * 我方支付类型
     */
    private String payType;
}
