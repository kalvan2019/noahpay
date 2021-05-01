package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelSignQueryRequest extends ChannelBaseRequest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 原订单发往银行流水
     */
    private String orgChannelSendSn;
    /**
     * 通道协议号
     */
    private String bankProtocol;
    /**
     * 银行户名
     */
    private String bankAccountName;
    /**
     * 银行账号
     */
    private String bankAccountNo;
    /**
     * 手机号码
     */
    private String mobile;
    /***
     * 通道证件类型
     */
    private String certificateNo;
}
