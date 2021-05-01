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
 * 商户分润实体
 * 表名 merchant_profit
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "merchant_profit")
public class MerchantProfit implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ExcelProperty(value = "编号",converter = LongStringConverter.class)
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 交易流水号
     */
    @ExcelProperty(value = "交易流水号")
    @Column(name = "trans_id")
    private String transId;

    /**
     * 商户号
     */
    @ExcelProperty(value = "商户号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 会计日期
     */
    @ExcelProperty(value = "会计日期",converter = IntegerStringConverter.class)
    @Column(name = "account_date")
    private Integer accountDate;

    /**
     * 交易类型
     */
    @ExcelProperty(value = "交易类型",converter = LongStringConverter.class)
    @Column(name = "trans_type")
    private Long transType;

    /**
     * 交易总笔数
     */
    @ExcelProperty(value = "交易总笔数",converter = LongStringConverter.class)
    @Column(name = "total_count")
    private Long totalCount;

    /**
     * 交易总金额
     */
    @ExcelProperty(value = "交易总金额",converter = LongStringConverter.class)
    @Column(name = "total_amount")
    private Long totalAmount;

    /**
     * 交易总手续费
     */
    @ExcelProperty(value = "交易总手续费",converter = LongStringConverter.class)
    @Column(name = "total_fee_amount")
    private Long totalFeeAmount;

    /**
     * 交易总成本
     */
    @ExcelProperty(value = "交易总成本",converter = LongStringConverter.class)
    @Column(name = "total_cost_amount")
    private Long totalCostAmount;

    /**
     * 商户利润
     */
    @ExcelProperty(value = "商户利润",converter = LongStringConverter.class)
    @Column(name = "total_profit_amount")
    private Long totalProfitAmount;

    /**
     * 商户实际分润
     */
    @ExcelProperty(value = "商户实际分润",converter = LongStringConverter.class)
    @Column(name = "fact_profit_amount")
    private Long factProfitAmount;

    /**
     * 实际入账金额
     */
    @ExcelProperty(value = "实际入账金额",converter = LongStringConverter.class)
    @Column(name = "fact_income_amount")
    private Long factIncomeAmount;

    /**
     * 发票金额
     */
    @ExcelProperty(value = "发票金额",converter = LongStringConverter.class)
    @Column(name = "invoice_amount")
    private Long invoiceAmount;

    /**
     * 会计入账日期
     */
    @ExcelProperty(value = "会计入账日期",converter = IntegerStringConverter.class)
    @Column(name = "accounting_date")
    private Integer accountingDate;

    /**
     * 记账流水号
     */
    @ExcelProperty(value = "记账流水号")
    @Column(name = "account_trans_id")
    private String accountTransId;

    /**
     * 开票流水号
     */
    @ExcelProperty(value = "开票流水号")
    @Column(name = "invoice_id")
    private String invoiceId;

    /**
     * 入账状态
     */
    @ExcelProperty(value = "入账状态",converter = IntegerStringConverter.class)
    @Column(name = "state")
    private Integer state;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @ExcelProperty(value = "修改时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

}
