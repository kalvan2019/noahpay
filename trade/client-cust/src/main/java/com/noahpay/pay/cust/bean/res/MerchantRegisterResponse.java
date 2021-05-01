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
public class MerchantRegisterResponse implements Serializable {
    /**
     * 商户编号
     */
    @NotNull
    private Long merchantNo;
    /**
     * 状态
     *
     * @see CustStateEnum
     */
    @NotNull
    private Integer state;
}
