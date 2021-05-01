package com.noahpay.pay.trade.process.template;

import com.kalvan.client.exception.BizException;
import com.noahpay.pay.trade.constant.TransReturnCode;

/**
 * 交易操作模板
 *
 * @author chenliang
 */
public abstract class BaseChannelTrans<B> {

    /**
     * 发往通道参数转换
     *
     * @param bill 流水
     */
    public void channelConvertParam(B bill) {
        throw new BizException(TransReturnCode.FAIL.formatMessage("未实现的方法"));
    }

    /**
     * 发往通道
     *
     * @param bill 流水
     * @return 流水
     */
    public B channelTrans(B bill) {
        throw new BizException(TransReturnCode.FAIL.formatMessage("未实现的方法"));
    }

    /**
     * 确认支付
     *
     * @param smsCode 验证码
     * @return 流水
     */
    public B channelTransConfirm(B bill, String smsCode) {
        throw new BizException(TransReturnCode.FAIL.formatMessage("未实现的方法"));
    }

    /**
     * 向通道查询
     *
     * @param bill 流水
     * @return 流水
     */
    public B channelQuery(B bill) {
        throw new BizException(TransReturnCode.FAIL.formatMessage("未实现的方法"));
    }
}
