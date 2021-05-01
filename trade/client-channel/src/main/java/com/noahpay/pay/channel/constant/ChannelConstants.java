package com.noahpay.pay.channel.constant;

/**
 * @author chenliang
 */
public interface ChannelConstants {
    /**
     * 0 转换
     */
    Integer CONVERT_YES = 0;
    /**
     * 0 生效
     */
    Integer CHANNEL_EFFECT = 0;
    /**
     * 银行路由开关,0启用
     */
    Integer BANK_ROUTE_FLAG = 0;
    /**
     * 大商户轮询,0转换1不转换
     */
    Integer MERCHANT_POOL_FLAG = 0;
    /**
     * 转换多商户,0转换1不转换
     */
    Integer MULTI_MERCHANT_FLAG = 0;
}
