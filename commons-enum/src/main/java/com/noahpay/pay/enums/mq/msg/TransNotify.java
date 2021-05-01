package com.noahpay.pay.enums.mq.msg;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenliang
 */
@Getter
@Setter
public class TransNotify extends BaseNotify implements Serializable {
    static final long serialVersionUID = 1L;
    /**
     * 交易状态 <br>
     * 0 业务成功(如果是交易表示交易成功)<br>
     * 其它需要根据码由调用方自己去处理逻辑<br>
     * 资金类:明确成功是成功，明确失败是失败，其它都当超时<br>
     */
    Integer state;

    /**
     * 交易流水号
     */
    String transId;
    /**
     * 交易类型
     */
    Integer transType;
    /**
     * 订单日期
     */
    Integer orderDate;
    /**
     * 订单号
     */
    String orderId;
    /**
     * 商户手续费
     */
    Long orderFee;
    /**
     * 交易会计日期
     */
    protected Integer accountDate;
    /**
     * 交易金额,单位分
     */
    protected Long orderAmount;
    /**
     * 商户手续费
     */
    private Long merchantFee;
    /**
     * 子商户手续费
     */
    private Long subMerchantFee;

    /**
     * 交易对手手续费
     */
    private Long consumerFee;

    /**
     * 交易返回码
     */
    private String resultCode;

    /**
     * 交易返回说明
     */
    private String resultNote;

    /**
     * 退票状态
     */
    private Integer returnTicketState;

    /**
     * 商户号
     */
    protected Long merchantNo;

}
