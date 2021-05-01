package com.noahpay.pay.trade.process.template.factory;

import com.noahpay.pay.trade.process.template.*;

/**
 * 默认的交易抽象工厂，如果需要其它个性化可再扩展自定义
 * 该工厂负责创建线上标准的业务逻辑对象，如果有个性化和自定义的其它对象需要创建则需要再定义其它工厂
 *
 * @author chenliang
 */
public interface ITradeProcessCreator {
    /**
     * 检查交易请求入参
     *
     * @return BaseCheck
     */
    BaseCheck<?, ?> factoryCheck();

    /**
     * 初始化交易流水
     *
     * @return BaseInit
     */
    BaseInit<?, ?> factoryInit();

    /**
     * 交易计费
     *
     * @return BaseFee
     */
    BaseFee factoryFee();

    /**
     * 交易路由
     *
     * @return BaseRoute
     */
    BaseRoute factoryRoute();

    /**
     * 风控检查
     *
     * @return BaseRisk
     */
    BaseRisk factoryRisk();

    /**
     * 交易实时处理
     *
     * @return BaseAccount
     */
    BaseAccount factoryAccount();

    /**
     * 交易业务处理
     *
     * @return BaseTrans
     */
    BaseChannelTrans factoryTrans();

    /**
     * 通道统计
     *
     * @return BaseChannelIncr
     */
    BaseStatistics factoryStatistics();

}
