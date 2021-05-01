package com.noahpay.pay.channel.bean.res;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用通道接口返回参数对象
 *
 * @author chenliang
 */
@Setter
@Getter
public class ChannelTransResponse implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * (必填) 核心统一设值
     */
    private Integer channelNo;
    /**
     * (组合必选)发送给通道的流水号(支付平台产生)
     */
    private String channelSendSn;
    /**
     * (组合必选)银行返回业务流水，如果是批量交易对应银行返回的批次流水
     */
    private String channelRecvSn;
    /**
     * (选填)通道返回金额,如果填了交易会检验(单笔交易使用)
     */
    private Long channelRecvAmount;
    /**
     * (选填)通道返回的银行会计日期
     */
    private Integer channelRecvAccountDate;
    /**
     * 交易是否需要验证确认
     */
    private Boolean confirmFlag;
    /**
     * 网关支付
     */
    private String gatewayPayHtml;
    /**
     * 二维码
     */
    private String qrCode;
}
