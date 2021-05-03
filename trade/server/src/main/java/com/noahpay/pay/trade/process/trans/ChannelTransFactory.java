package com.noahpay.pay.trade.process.trans;

import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.trade.constant.PayTypeEnum;
import com.noahpay.pay.trade.constant.TransReturnCode;
import com.noahpay.pay.trade.process.template.BaseChannelTrans;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 默认通道交易工厂，如果需要其它个性化可再扩展自定义
 *
 * @author chenliang
 */
@Component
public class ChannelTransFactory {

    @Resource
    WxPay wxPay;

    /**
     * 获取通道支付交易处理器
     *
     * @param payBill 支付明细
     * @return 处理器
     */
    public BaseChannelTrans<PayBill> getChannelTrans(PayBill payBill) {
        if (PayTypeEnum.NATIVE.code.equals(payBill.getPayType())) {
            return wxPay;
        }
        if (PayTypeEnum.JSAPI.code.equals(payBill.getPayType())) {
            return wxPay;
        }
        if (PayTypeEnum.MICROPAY.code.equals(payBill.getPayType())) {
            return wxPay;
        }
        if (PayTypeEnum.APP.code.equals(payBill.getPayType())) {
            return wxPay;
        }
        if (PayTypeEnum.MWEB.code.equals(payBill.getPayType())) {
            return wxPay;
        }
        throw new BizException(TransReturnCode.REQUEST_SERVICE_NOT_SUPPORT.formatMessage(payBill.getPayType()));
    }

}
