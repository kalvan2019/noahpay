package com.noahpay.pay.trade.event;

import com.kalvan.enums.mq.MqTopicEnum;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 消息信道定义
 *
 * @author chenliang
 */
public interface MqOutput {

    @Output(MqTopicEnum.TRADE_NOTIFY)
    MessageChannel tradeNotify();

}