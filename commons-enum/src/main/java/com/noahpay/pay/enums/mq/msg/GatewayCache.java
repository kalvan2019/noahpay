package com.noahpay.pay.enums.mq.msg;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Getter
@Setter
public class GatewayCache {
    /**
     * appId
     */
    String appId;

    /**
     * 服务
     */
    String service;
}
