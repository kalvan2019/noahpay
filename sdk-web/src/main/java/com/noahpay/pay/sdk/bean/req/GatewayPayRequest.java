package com.noahpay.pay.sdk.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 支付请求
 *
 * @author chenliang
 */
@Getter
@Setter
public class GatewayPayRequest {
    /**
     * 接入方id
     */
    private String appId;
    /**
     * 收银台或者纯网关
     * cashier
     * direct
     */
    private String payMode;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 行别
     */
    private String bankType;
    /**
     * 账户类型
     */
    private String bankAccountType;
    /**
     * 限制卡类型
     */
    private String supportCardType;
}