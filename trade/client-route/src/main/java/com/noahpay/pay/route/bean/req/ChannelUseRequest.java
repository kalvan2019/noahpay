package com.noahpay.pay.route.bean.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 通道使用额
 *
 * @author chenliang
 */
@Getter
@Setter
public class ChannelUseRequest implements java.io.Serializable {

    /**
     * 通道商户池日使用额
     * 通道号、商户号、子商户号、使用金额
     */
    private Map<Integer, Map<String, Map<String, Long>>> channelMerchantUseAmount;
    /**
     * 通道使用额
     * 支付类型、通道号、使用额
     */
    private Map<String, Map<Integer, Long>> channelUseAmount;
    /**
     * 通道使用笔数
     * 支付类型、通道号、使用次数
     */
    private Map<String, Map<Integer, Long>> channelUseNumber;
}
