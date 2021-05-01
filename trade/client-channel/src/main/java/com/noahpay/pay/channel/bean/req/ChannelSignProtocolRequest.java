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
public class ChannelSignProtocolRequest extends ChannelBaseRequest implements
        java.io.Serializable {
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
     * 银行名
     */
    private String bankName;
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
     * 银行卡有效期
     * MMyy
     */
    private String bankAccountExpiry;
    /**
     * cvv2
     */
    private String cvv2;
    /***
     * 通道证件类型
     */
    private String certificateType;
    /***
     * 通道证件类型
     */
    private String certificateNo;
    /**
     * 协议生效日期 yyyyMMdd
     */
    private String effectiveDate;
    /**
     * 协议失效日期 yyyyMMdd
     */
    private String expiryDate;
    /**
     * 通道业务代码
     */
    private String mcc;
    /**
     * 客户端ip
     */
    private String terminalIp;
    /**
     * 商户服务器ip
     */
    private String orderIp;
}
