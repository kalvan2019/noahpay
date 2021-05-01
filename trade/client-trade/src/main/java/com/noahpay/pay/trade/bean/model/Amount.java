package com.noahpay.pay.trade.bean.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 订单金额信息
 *
 * @author chenliang
 */
@Getter
@Setter
public class Amount implements java.io.Serializable {
    /**
     * 总金额
     * 订单总金额，币种的最小单位，只能为整数，详见交易金额
     */
    @NotNull(message = "总金额不能为空")
    private Long total;

    /**
     * 货币类型
     * 符合ISO 4217标准的三位字母代码 如：USD
     */
    @NotBlank(message = "货币类型不能为空")
    private String currency;
}
