package com.noahpay.pay.trade.process.template;

/**
 * 计费操作模板
 *
 * @author chenliang
 */
public abstract class BaseFee<B> {

    /**
     * 交易计费
     *
     * @param bill 流水
     */
    public abstract void calcFee(B bill);
}
