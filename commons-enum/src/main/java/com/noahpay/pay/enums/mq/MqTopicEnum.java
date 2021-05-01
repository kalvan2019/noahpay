package com.noahpay.pay.enums.mq;


/**
 * @author kalvan
 */
public interface MqTopicEnum {
    /**
     * 应用监控
     */
    String MONITOR = "monitor";
    /**
     * 商户通知TOPIC
     */
    String MERCHANT_NOTIFY = "merchantNotify";
    /**
     * 交易通知
     */
    String TRADE_NOTIFY = "tradeNotify";
    /**
     * 交易通知结果
     */
    String TRADE_NOTIFY_RESULT = "tradeNotifyResult";
}
