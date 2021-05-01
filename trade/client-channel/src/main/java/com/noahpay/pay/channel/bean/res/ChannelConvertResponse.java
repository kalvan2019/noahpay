package com.noahpay.pay.channel.bean.res;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelConvertResponse implements
        java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 发往银行流水号
     */
    private String channelSendSn;
    /***
     * 通道证件类型
     */
    private String channelCertificateType;
    /**
     * 通道行别
     */
    private String channelBankType;
    /**
     * 账户类型
     */
    private String channelBankAccountType;
    /**
     * 通道地区码
     */
    private String channelCity;
    /**
     * 通道业务代码
     */
    private String channelMcc;
}
