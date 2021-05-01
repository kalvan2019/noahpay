package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用通道快捷支付协议签订接口需要的参数对象
 *
 * @author chenliang
 */
@Setter
@Getter
public class ChannelSignProtocolConfirmRequest extends ChannelBaseRequest implements
        java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 银行账号
     */
    private String bankAccountNo;
    /**
     * 银行户名
     */
    private String bankAccountName;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 签约确认需要,签约申请流水号
     */
    private String orgChannelSendSn;
    /**
     * 签约确认需要,银行协议号
     */
    private String bankProtocol;
    /**
     * 签约确认需要,短信验证码
     */
    private String smsCode;
    private String bankAccountType;
    /**
     * 通道行别
     */
    private String bankType;
    /**
     * 银行卡有效期
     * MMyy
     */
    private String bankAccountExpiry;
    /**
     * cvv2
     */
    private String cvv2;
    /***
     * 通道证件号码
     */
    private String certificateNo;
    /***
     * 交易透传信息
     */
    private String thpinfo;
    /**
     * 商户服务器ip
     */
    private String orderIp;
}
