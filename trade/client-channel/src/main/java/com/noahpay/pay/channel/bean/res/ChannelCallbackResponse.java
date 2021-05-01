package com.noahpay.pay.channel.bean.res;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelCallbackResponse extends ChannelTransResponse implements
        java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 通道协议号
     */
    private String bankProtocol;
    /**
     * 通知类型，指常量类NotifyType
     */
    private String notifyType;
    /**
     * (选填)通道返回的通知时间(通道的服务器时间)，为空时默认当前支付平台服务器时间
     */
    private Date notifyTime;
    /**
     * 返回上游握手报文
     */
    private String responseMsg;
    /**
     * API网关前台跳转地址
     */
    private String apiPgUrl;
    private String cardNo;
    private String cardNoSensitive;
    /**
     * 通道子商户号
     */
    private String channelSubMerchantNo;
}
