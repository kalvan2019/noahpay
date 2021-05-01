package com.noahpay.pay.channel.bean.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Setter
@Getter
public class DeviceInfo implements java.io.Serializable {
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备号
     */
    private String deviceNo;
    /**
     * 终端ip
     */
    private String terminalIp;
}
