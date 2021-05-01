package com.noahpay.pay.channel.bean.res;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用通道快捷支付协议签订接口返回对象
 *
 * @author chenliang
 */
@Setter
@Getter
public class ChannelSignProtocolResponse extends ChannelTransResponse implements
        java.io.Serializable {
    private static final long serialVersionUID = 1L;
    String cardNo;
    String cardNoSensitive;
    /**
     * 通道协议号
     */
    private String bankProtocol;
    /**
     * 银行账户等级
     */
    private Integer bankAccountLevel;
    /**
     * 签约是否需要验证确认
     */
    private Boolean confirmFlag;
    /**
     * H5签约跳转URL
     */
    private String signH5Url;
}
