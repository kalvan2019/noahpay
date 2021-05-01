package com.noahpay.pay.cust.bean.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 客户绑卡查询
 *
 * @author chenliang
 */
@Getter
@Setter
public class ProtocolQueryRequest implements Serializable {
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 银行卡号
     */
    private String bankAccountNo;
    /**
     * 支付方式
     */
    private String payType;
}
