package com.noahpay.pay.cust.bean.model;

import com.noahpay.pay.enums.regexp.RegExpEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 卡信息
 *
 * @author chenliang
 */
@Getter
@Setter
public class BankCardInfo implements Serializable {
    /**
     * 银行卡号
     */
    @NotBlank(message = "银行卡号不能为空")
    private String bankAccountNo;
    /**
     * 银行账户名
     */
    @NotBlank(message = "银行账户名不能为空")
    private String bankAccountName;
    /**
     * 银行卡预留手机号
     */
    @Pattern(regexp = RegExpEnum.MOBILE_PHONE)
    @NotBlank(message = "预留手机号不能为空")
    private String mobile;
    /**
     * 银行卡有效期
     * MMyy 格式
     */
    @NotBlank(message = "银行卡有效期不能为空")
    private String bankAccountExpiry;
    /**
     * 账户类型
     */
    private Integer bankAccountType;
    /**
     * 行别
     */
    private String bankType;
    /**
     * 行名
     */
    private String bankName;
}
