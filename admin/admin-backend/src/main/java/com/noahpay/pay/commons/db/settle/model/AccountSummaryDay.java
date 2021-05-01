package com.noahpay.pay.commons.db.settle.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 账户余额日终报表实体
 * 表名 account_summary_day
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "account_summary_day")
public class AccountSummaryDay implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @ExcelProperty(value = "自增id",converter = LongStringConverter.class)
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 日期
     */
    @ExcelProperty(value = "日期",converter = IntegerStringConverter.class)
    @Column(name = "account_date")
    private Integer accountDate;

    /**
     * 上日日终余额
     */
    @ExcelProperty(value = "上日日终余额",converter = LongStringConverter.class)
    @Column(name = "total_amount_before")
    private Long totalAmountBefore;

    /**
     * 本日日终余额
     */
    @ExcelProperty(value = "本日日终余额",converter = LongStringConverter.class)
    @Column(name = "total_amount_today")
    private Long totalAmountToday;

    /**
     * 入账
     */
    @ExcelProperty(value = "入账",converter = LongStringConverter.class)
    @Column(name = "income_amount")
    private Long incomeAmount;

    /**
     * 分润入账
     */
    @ExcelProperty(value = "分润入账",converter = LongStringConverter.class)
    @Column(name = "income_profit_amount")
    private Long incomeProfitAmount;

    /**
     * 出账
     */
    @ExcelProperty(value = "出账",converter = LongStringConverter.class)
    @Column(name = "outgo_amount")
    private Long outgoAmount;

    /**
     * 分润扣款
     */
    @ExcelProperty(value = "分润扣款",converter = LongStringConverter.class)
    @Column(name = "outgo_profit_amount")
    private Long outgoProfitAmount;

    /**
     * 手续费
     */
    @ExcelProperty(value = "手续费",converter = LongStringConverter.class)
    @Column(name = "fee_amount")
    private Long feeAmount;

    /**
     * 平账状态
     */
    @ExcelProperty(value = "平账状态",converter = IntegerStringConverter.class)
    @Column(name = "balance_state")
    private Integer balanceState;

    /**
     * 交易账户平账状态
     */
    @ExcelProperty(value = "交易账户平账状态",converter = IntegerStringConverter.class)
    @Column(name = "check_state")
    private Integer checkState;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @Column(name = "remark")
    private String remark;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

}
