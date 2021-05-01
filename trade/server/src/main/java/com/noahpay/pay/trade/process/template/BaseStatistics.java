package com.noahpay.pay.trade.process.template;

import org.springframework.scheduling.annotation.Async;

/**
 * 数据统计操作模板
 *
 * @author chenliang
 */
public abstract class BaseStatistics<B> {

    /**
     * 处理通道成功的各类统计
     * 支付类型日使用额累计/支付类型月使用额累计/商户轮训池日使用额累计/商户轮训池月使用额累计
     *
     * @param bill    流水
     */
    @Async
    public abstract void successCount(B bill);

    /**
     * 通道统计冲正，目前只有代付场景使用到
     *
     * @param bill 流水
     */
    @Async
    public abstract void undoCount(B bill);
}
