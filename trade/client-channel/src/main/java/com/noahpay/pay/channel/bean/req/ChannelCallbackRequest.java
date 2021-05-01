package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelCallbackRequest extends ChannelBaseRequest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 通道异步通知数据
     */
    private Map<String, String> channelCallBackParams;
    /**
     * 通道异步通知类型
     */
    private String notifyType;
    /**
     * 访问action名称
     */
    private String actionName;
}
