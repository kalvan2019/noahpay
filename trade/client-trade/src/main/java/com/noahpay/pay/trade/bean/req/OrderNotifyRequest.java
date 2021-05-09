package com.noahpay.pay.trade.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 交易通知参数
 *
 * @author chenliang
 */
@Getter
@Setter
public class OrderNotifyRequest implements java.io.Serializable {
    /**
     * 交易流水号
     */
    @NotBlank(message = "交易流水号不能为空")
    private String transId;
    /**
     * 通知状态
     */
    @NotNull(message = "通知状态不能为空")
    private Integer notifyState;
}
