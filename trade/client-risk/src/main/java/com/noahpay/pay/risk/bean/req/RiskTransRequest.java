package com.noahpay.pay.risk.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 交易风控
 *
 * @author chenliang
 */
@Getter
@Setter
public class RiskTransRequest extends RiskBlackRequest implements java.io.Serializable {
    /**
     * 交易金额
     */
    @NotNull(message = "交易金额不能为空")
    private Long tradeAmount;
    /**
     * 资金方向
     * 0-入 1-出
     */
    @NotNull(message = "资金方向不能为空")
    private Integer amountFlag;
    /**
     * 交易日期
     */
    @NotNull(message = "交易日期不能为空")
    private Integer transDate;
    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    private Long merchantNo;
    /**
     * 交易类型
     */
    @NotNull(message = "交易类型不能为空")
    private Integer transType;
    /**
     * 日使用笔数
     */
    @NotNull(message = "日限笔数不能为空")
    private Long dayUseLimitNumber;
    /**
     * 日使用金额
     */
    @NotNull(message = "日限金额不能为空")
    private Long dayUseLimitAmount;
    /**
     * 月使用笔数
     */
    @NotNull(message = "月限笔数不能为空")
    private Long monthUseLimitNumber;
    /**
     * 月使用金额
     */
    @NotNull(message = "月限金额不能为空")
    private Long monthUseLimitAmount;

}
