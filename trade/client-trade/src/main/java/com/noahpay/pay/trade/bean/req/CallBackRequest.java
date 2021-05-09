package com.noahpay.pay.trade.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Getter
@Setter
public class CallBackRequest implements java.io.Serializable {
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
     * 通道返回附加数据
     * json格式
     */
    private String channelRecvExt;
    /**
     * 交易状态
     * @see com.kalvan.client.constant.CommonStateEnum
     */
    private int state;
    /**
     * 渠道返回码
     */
    private String code;
    /**
     * 渠道返回说明
     */
    private String message;
}
