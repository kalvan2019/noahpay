package com.noahpay.pay.trade.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 订单查询参数
 *
 * @author chenliang
 */
@Getter
@Setter
public class OrderQueryRequest implements java.io.Serializable {
    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    private Long merchantNo;
    /**
     * 商户订单号
     * <=32位
     */
    @Size(max = 32, message = "订单号")
    @NotBlank(message = "订单号不能为空")
    private String orderId;
    /**
     * 商户订单日期
     */
    @NotNull(message = "订单日期不能为空")
    private Integer orderDate;
    /**
     * 交易流水号
     */
    private String transId;
}
