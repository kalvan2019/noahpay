package com.noahpay.pay.trade.process.template;

/**
 * 检查操作模板
 *
 * @author chenliang
 */
public abstract class BaseCheck<O, B> {

    /**
     * 检查传入参数
     *
     * @param request 请求参数
     */
    public abstract void checkParams(O request);

    /**
     * 检查订单是否重复
     */
    public abstract void checkRepeat(O request);

    /**
     * 检查订单业务
     *
     * @param bill 流水
     */
    public abstract void checkBusiness(B bill);
}
