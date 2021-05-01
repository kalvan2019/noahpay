package com.noahpay.pay.trade.process.template;

/**
 * 路由操作模板
 *
 * @author chenliang
 */
public abstract class BaseRoute<B> {

    /**
     * 交易路由
     *
     * @param bill    流水
     */
    public abstract void route(B bill);
}
