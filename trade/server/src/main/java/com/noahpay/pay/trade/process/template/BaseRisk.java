package com.noahpay.pay.trade.process.template;

/**
 * 风控操作模板
 *
 * @author chenliang
 */
public abstract class BaseRisk<B> {

    /**
     * 检查风控
     *
     * @param bill    流水
     */
    public abstract void checkRisk(B bill);
}
