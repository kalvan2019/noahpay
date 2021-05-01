package com.noahpay.pay.trade.process.template;

/**
 * 初始化操作模板
 *
 * @author chenliang
 */
public abstract class BaseInit<O, B> {

    /**
     * 根据参数对象初始化数据库流水对象O返回
     *
     * @param request 请求参数
     * @return 流水
     */
    public abstract B initBill(O request);
}
