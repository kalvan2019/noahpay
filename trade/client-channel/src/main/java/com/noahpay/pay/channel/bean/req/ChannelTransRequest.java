package com.noahpay.pay.channel.bean.req;

import com.noahpay.pay.channel.bean.model.DeviceInfo;
import com.noahpay.pay.channel.bean.model.ExtDataInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 向通道发送订单交易传入参数
 *
 * @author chenliang
 */
@Setter
@Getter
public class ChannelTransRequest extends ChannelBaseRequest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 通道行别
     */
    private String channelBankType;
    /**
     * 账户类型
     */
    private String channelBankAccountType;
    /**
     * 银行名
     */
    private String bankName;
    /**
     * 银行账号
     */
    private String bankAccountNo;
    /**
     * 银行账号有效期
     * MMyy
     */
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
    private String channelCertificateType;
    /***
     * 通道证件号
     */
    private String certificateNo;
    /**
     * 通道城市编码
     */
    private String channelCity;
    /**
     * 通道业务代码
     */
    private String channelMcc;
    /**
     * 交易金额,单位分
     */
    private Long channelAmount;
    /**
     * 订单说明
     */
    private String orderNote;
    /**
     * 商户服务器ip
     */
    private String orderIp;
    /**
     * 设备信息
     */
    private DeviceInfo deviceInfo;
    /**
     * 交易扩展参数，对应的key可以查看ExtDataKey类
     */
    private ExtDataInfo extDataInfo;
    /**
     * 二维码
     */
    private String qrCode;
}
