package com.noahpay.pay.fee.bean.req;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 商户计费参数
 *
 * @author chenliang
 */
@Getter
@Setter
public class FeeCalculateRequest implements java.io.Serializable {
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
     * 交易类型
     * 参照交易系统定义枚举
     */
    @NotNull(message = "交易类型不可以为空")
    private Integer transType;
    /**
     * 支付类型
     * 参照通道系统定义枚举
     */
    @NotBlank(message = "支付类型不可以为空")
    private String payType;
    /**
     * 行别
     * 参照数据字典
     */
    @NotBlank(message = "行别不可以为空")
    private String bankType;
    /**
     * 银行账户类型
     * 参照数据字典
     */
    @NotNull(message = "银行账户类型不可以为空")
    private Integer bankAccountType;
    /**
     * 交易金额(分)
     */
    private Long amount;
    /**
     * 平台外计算手续费(分)
     */
    private Long subMerchantFee;
    /**
     * 通道号
     */
    private Integer channelNo;
}
