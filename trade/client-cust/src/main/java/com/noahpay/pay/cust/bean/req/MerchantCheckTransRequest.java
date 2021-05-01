package com.noahpay.pay.cust.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商户入网检查
 *
 * @author chenliang
 */
@Getter
@Setter
public class MerchantCheckTransRequest implements Serializable {
    /**
     * 商户号
     */
    @NotNull(message = "商户号不可以为空")
    private Long merchantNo;
    /**
     * 交易类型
     */
    @NotNull(message = "交易类型不可以为空")
    private Integer transType;
    /**
     * 交易金额
     */
    @NotNull(message = "交易金额不可以为空")
    private Long amount;
}
