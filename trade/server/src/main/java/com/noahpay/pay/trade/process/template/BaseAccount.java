package com.noahpay.pay.trade.process.template;

/**
 * 账户操作模板
 *
 * @author chenliang
 */
public abstract class BaseAccount<B> {

    /**
     * 出账
     *
     * @param bill 流水
     */
    public boolean accountOut(B bill) {
        return false;
    }

    /**
     * 出账冲正 交易失败时,或者记账超时时调用,支持重做
     *
     * @param bill 流水
     */
    public boolean accountOutUndo(B bill) {
        return false;
    }

    /**
     * 入账
     *
     * @param bill 流水
     */
    public boolean accountIn(B bill) {
        return false;
    }
}
