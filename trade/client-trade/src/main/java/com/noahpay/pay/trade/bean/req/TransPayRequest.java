package com.noahpay.pay.trade.bean.req;

import com.noahpay.pay.trade.constant.PayTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 交易参数
 *
 * @author chenliang
 */
@Getter
@Setter
public class TransPayRequest implements java.io.Serializable {
    /**
     * 支付方式
     *
     * @see PayTypeEnum
     */
    private String payType;
    /**
     * 交易流水号
     */
    private String transId;
    /**
     * 交易对应的支付明细id
     * 快捷支付确认付款时需要
     */
    private Long payId;
}
