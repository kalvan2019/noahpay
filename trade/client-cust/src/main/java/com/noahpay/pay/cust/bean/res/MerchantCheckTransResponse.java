package com.noahpay.pay.cust.bean.res;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商户入网检查
 *
 * @author chenliang
 */
@Getter
@Setter
public class MerchantCheckTransResponse implements Serializable {
    /**
     * 商户名
     */
    private String merchantName;
    /**
     * 单笔交易最大额
     */
    private Long limitMaxAmount;
    /**
     * 日交易限额
     */
    private Long dayLimitAmount;
    /**
     * 日限制笔数
     */
    private Long dayLimitNumber;
    /**
     * 月交易限额
     */
    private Long monthLimitAmount;
    /**
     * 月限制笔数
     */
    private Long monthLimitNumber;
}
