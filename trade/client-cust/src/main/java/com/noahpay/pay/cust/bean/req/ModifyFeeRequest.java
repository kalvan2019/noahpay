package com.noahpay.pay.cust.bean.req;

import com.noahpay.pay.cust.bean.model.FeeInfo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 客户费率修改
 *
 * @author chenliang
 */
@Getter
@Setter
public class ModifyFeeRequest implements Serializable {
    /**
     * 商户号
     */
    @NotNull(message = "商户号不可以为空")
    private Long merchantNo;
    /**
     * 子商户号
     */
    private Long subMerchantNo;
    /**
     * 客户手续费设置
     */
    @Valid
    private FeeInfo feeInfo;
}
