package com.noahpay.pay.trade.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 订单查询参数
 *
 * @author chenliang
 */
@Getter
@Setter
public class OrderManualRequest implements java.io.Serializable {
    /**
     * 交易流水号
     */
    @NotBlank(message = "交易流水号不能为空")
    public String transId;
    /**
     * 交易状态置为成功或者失败
     */
    @NotNull(message = "交易状态不能为空")
    public Boolean success;
    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空")
    public String remark;
}
