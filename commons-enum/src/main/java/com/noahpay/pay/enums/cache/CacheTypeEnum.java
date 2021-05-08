package com.noahpay.pay.enums.cache;


import com.kalvan.enums.cache.PlatformCacheTypeEnum;

/**
 * 缓存type
 *
 * @author kalvan
 */
public interface CacheTypeEnum  extends PlatformCacheTypeEnum {
    /**
     * cardBin
     */
    String BANK_CARD_BIN = "BANK_CARD_BIN";
    /**
     * 计费
     */
    String FEE_MERCHANT = "FEE_MERCHANT";
    /**
     * 计费
     */
    String FEE_RULE = "FEE_RULE";
    /**
     * 计费
     */
    String FEE_SEGMENT_RULE = "FEE_SEGMENT_RULE";
    /**
     * 黑名单
     */
    String BLACK_LIST = "BLACK_LIST";
    /**
     * 商户
     */
    String MERCHANT = "MERCHANT";
    /**
     * 商户交易入网协议
     */
    String MERCHANT_PRODUCT_TRANS = "MERCHANT_PRODUCT_TRANS";
    /**
     * 渠道信息
     */
    String CHANNEL_INFO = "CHANNEL_INFO";
    /**
     * 渠道信息
     */
    String CHANNEL_EXT_PARAM = "CHANNEL_EXT_PARAM";
    /**
     * 商户路由信息
     */
    String ROUTE_MERCHANT = "ROUTE_MERCHANT";
    /**
     * 路由规则
     */
    String ROUTE_RULE = "ROUTE_RULE";
    /**
     * 渠道支付方式配置
     */
    String CHANNEL_SUPPORT_PAY_TYPE = "CHANNEL_SUPPORT_PAY_TYPE";
    /**
     * 渠道支持银行配置
     */
    String CHANNEL_SUPPORT_BANK = "CHANNEL_SUPPORT_BANK";
}
