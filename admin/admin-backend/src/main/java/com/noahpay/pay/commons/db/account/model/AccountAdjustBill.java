package com.noahpay.pay.commons.db.account.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.admin.annotation.NumberConvert;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 账户调账流水实体
 * 表名 account_adjust_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "account_adjust_bill")
public class AccountAdjustBill implements Serializable {
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
     * 调账流水id
     */
    @ExcelProperty(value = "调账流水id")
    @Column(name = "adjust_trans_id")
    private String adjustTransId;

    /**
     * 交易类型 20-调账
     */
    @ExcelProperty(value = "交易类型 20-调账",converter = IntegerStringConverter.class)
    @Dict(DictType.TRANS_TYPE)
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 凭证类型
     */
    @ExcelProperty(value = "凭证类型",converter = IntegerStringConverter.class)
    @Dict(DictType.VOUCHER_TYPE)
    @Column(name = "voucher_type")
    private Integer voucherType;

    /**
     * 记账流水
     */
    @ExcelProperty(value = "记账流水")
    @Column(name = "account_trans_id")
    private String accountTransId;

    /**
     * 会计日期
     */
    @ExcelProperty(value = "会计日期",converter = IntegerStringConverter.class)
    @Column(name = "account_date")
    private Integer accountDate;

    /**
     * 账户号
     */
    @ExcelProperty(value = "账户号",converter = LongStringConverter.class)
    @Column(name = "account_no")
    private Long accountNo;

    /**
     * 客户号
     */
    @ExcelProperty(value = "客户号",converter = LongStringConverter.class)
    @Column(name = "cust_no")
    private Long custNo;

    /**
     * 入账金额
     */
    @ExcelProperty(value = "入账金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "income_amount")
    private Long incomeAmount;

    /**
     * 出账金额
     */
    @ExcelProperty(value = "出账金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "outgo_amount")
    private Long outgoAmount;

    /**
     * 调账原因
     */
    @ExcelProperty(value = "调账原因")
    @Column(name = "adjust_reason")
    private String adjustReason;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
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
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

}
