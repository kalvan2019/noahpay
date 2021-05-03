package com.noahpay.pay.trade.bean.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 场景信息
 *
 * @author chenliang
 */
@Getter
@Setter
public class SceneInfo implements java.io.Serializable {
    /**
     * 商户端设备号
     * 终端设备号(商户自定义，如门店编号)
     */
    private String deviceId;
    /**
     * 商户端设备IP
     * 商户侧设备IP，取公网出口IP，支持IPV6
     */
    private String deviceIp;
    /**
     * 用户终端IP
     * 用户侧设备IP，取公网出口IP，支持IPV6
     */
    private String payerClientIp;
    /**
     * 操作员ID
     * 收银员ID，由商户自定义
     */
    private String operatorId;
}
