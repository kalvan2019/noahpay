package com.noahpay.pay.route.bean.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 路由返回对象
 *
 * @author chenliang
 */
@Setter
@Getter
public class BankInfo implements java.io.Serializable {
    /**
     * 银行帐户类型
     */
    private Integer bankAccountType;
    /**
     * 行别
     */
    private String bankType;
}
