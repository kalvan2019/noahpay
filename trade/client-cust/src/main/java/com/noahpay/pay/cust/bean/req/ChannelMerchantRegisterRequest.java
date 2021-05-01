package com.noahpay.pay.cust.bean.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 通道商户注册
 *
 * @author chenliang
 */
@Getter
@Setter
public class ChannelMerchantRegisterRequest implements Serializable {
    /**
     * 商户号
     */
    private Long merchantNo;
    /**
     * 商户名
     */
    private String merchantName;
}
