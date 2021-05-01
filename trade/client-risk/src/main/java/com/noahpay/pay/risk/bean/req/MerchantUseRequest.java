package com.noahpay.pay.risk.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 交易风控
 *
 * @author chenliang
 */
@Getter
@Setter
public class MerchantUseRequest implements java.io.Serializable {
    /**
     * 交易类型
     */
    @NotNull(message = "交易类型不能为空")
    private Integer transType;
    /**
     * 金额累加
     */
    @NotEmpty(message = "金额累加不能为空")
    private Map<Long, Long> amountMap;
    /**
     * 笔数累加
     */
    @NotEmpty(message = "笔数累加不能为空")
    private Map<Long, Long> numberMap;
}
