package com.noahpay.pay.cust.bean.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 手续费信息
 *
 * @author chenliang
 */
@Getter
@Setter
public class FeeInfo implements Serializable {
    /**
     * 客户交易费率,单位万1
     */
    private Long custFeeRate;
    /**
     * 提现手续费,单位分
     */
    private Long custWithdrawFee;
}
