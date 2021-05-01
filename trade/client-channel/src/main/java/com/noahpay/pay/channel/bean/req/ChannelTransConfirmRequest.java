package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 向通道发送订单交易传入参数
 *
 * @author chenliang
 */
@Setter
@Getter
public class ChannelTransConfirmRequest extends ChannelBaseRequest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 通道行别
     */
    private String bankType;
    /**
     * 账户类型
     */
    private String bankAccountType;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行账号
     */
    private String bankAccountNo;
    private String bankAccountExpiry;
    /**
     * 银行户名
     */
    private String bankAccountName;
    /**
     * cvv2
     */
    private String cvv2;
    /**
     * 通道协议号
     */
    private String bankProtocol;
    /**
     * 银行账户等级
     */
    private String bankAccountLevel;
    /**
     * 手机号码
     */
    private String mobile;
    /***
     * 通道证件类型
     */
    private String certificateType;
    /***
     * 通道证件号
     */
    private String certificateNo;
    /**
     * 确认支付需要,短信验证码
     */
    private String smsCode;
    /**
     * 确认支付需要,原发往通道流水
     */
    private String orgChannelSendSn;
    /**
     * 确认支付需要,原通道返回流水
     */
    private String orgChannelRecvSn;
    /***
     * 交易透传信息
     */
    private String thpinfo;
}
