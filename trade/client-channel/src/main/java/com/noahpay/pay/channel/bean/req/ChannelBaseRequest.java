package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 传给通道基础参数
 *
 * @author chenliang
 */
@Setter
@Getter
public class ChannelBaseRequest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 通道号
     */
    private Integer channelNo;
    /**
     * 通道商户号
     */
    private String channelMerchantNo;
    /**
     * 通道商户名
     */
    private String channelMerchantName;
    /**
     * 通道子商户号
     */
    private String channelSubMerchantNo;
    /**
     * 本次业务发往银行流水
     */
    private String channelSendSn;
    /**
     * 本次业务发往银行时间
     */
    private Date channelSendTime;
}
