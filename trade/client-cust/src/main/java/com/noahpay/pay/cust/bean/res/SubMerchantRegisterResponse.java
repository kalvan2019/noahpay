package com.noahpay.pay.cust.bean.res;

import com.noahpay.pay.cust.constant.CustStateEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chenliang
 */
@Getter
@Setter
public class SubMerchantRegisterResponse implements Serializable {
    /**
     * 商户编号
     */
    @NotNull
    private Long merchantNo;
    /**
     * 子商户编号
     */
    @NotNull
    private Long subMerchantNo;
    /**
     * 状态
     *
     * @see CustStateEnum
     */
    @NotNull
    private Integer state;
}
