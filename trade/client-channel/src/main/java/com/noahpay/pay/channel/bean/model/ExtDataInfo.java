package com.noahpay.pay.channel.bean.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ExtDataInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    Integer signPayType;
    /**
     * 扩展参数-应用提供方账 户 ID(用户手 机 号 取 哈 希值）
     */
    private String accountIdHash;
    /**
     * 账户注册日期
     * yyyyMMddHHmmss
     */
    private String accountRegisterTime;
}
