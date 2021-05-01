package com.noahpay.pay.sdk.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 退款请求
 *
 * @author chenliang
 */
@Getter
@Setter
public class RefundRequest {
    /**
     * 接入方id
     */
    private String appId;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 退款金额
     */
    private String amount;
    /**
     * 平台流水号
     */
    private String transId;
}
