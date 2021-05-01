package com.noahpay.pay.fee.bean.res;

import com.noahpay.pay.fee.constant.FeeModelEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 计费返回
 *
 * @author chenliang
 */
@Getter
@Setter
public class FeeCalculateResponse implements Serializable {
    /**
     * 商户手续费(分)
     */
    @NotNull
    private Long merchantFee;
    /**
     * 子商户手续费(分)
     */
    @NotNull
    private Long subMerchantFee;
    /**
     * 平台外客户手续费(分)
     */
    private Long consumerFee;
    /**
     * 订单金额(分)
     */
    private Long amount;
    /**
     * 计费方式
     *
     * @see FeeModelEnum
     */
    private Integer feeModel;
}
