package com.noahpay.pay.cust.bean.res;

import com.noahpay.pay.cust.constant.CustStateEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 交易基础返回对象
 *
 * @author chenliang
 */
@Setter
@Getter
public class PartnerRegisterResponse implements java.io.Serializable {
    /**
     * 合作方编号
     */
    @NotNull
    private Long partnerNo;
    /**
     * 状态
     *
     * @see CustStateEnum
     */
    @NotNull
    private Integer state;
}
