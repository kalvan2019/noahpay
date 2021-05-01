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
 * 合作方分润实体
 * 表名 partner_profit
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "partner_profit")
public class PartnerProfit implements Serializable {
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
     * 合作方编号
     */
    @ExcelProperty(value = "合作方编号",converter = LongStringConverter.class)
    @Column(name = "partner_no")
    private Long partnerNo;

    /**
     * 商户编号
     */
    @ExcelProperty(value = "商户编号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 银行帐号
     */
    @ExcelProperty(value = "银行帐号")
    @Column(name = "bank_account_no")
    private String bankAccountNo;

    /**
     * 会计开始日期
     */
    @ExcelProperty(value = "会计开始日期",converter = IntegerStringConverter.class)
    @Column(name = "account_begin_date")
    private Integer accountBeginDate;

    /**
     * 会计结束日期
     */
    @ExcelProperty(value = "会计结束日期",converter = IntegerStringConverter.class)
    @Column(name = "account_end_date")
    private Integer accountEndDate;

    /**
     * 会计入账日期
     */
    @ExcelProperty(value = "会计入账日期",converter = IntegerStringConverter.class)
    @Column(name = "accounting_date")
    private Integer accountingDate;

    /**
     * 消费笔数
     */
    @ExcelProperty(value = "消费笔数",converter = LongStringConverter.class)
    @Column(name = "trans_total_count")
    private Long transTotalCount;

    /**
     * 消费金额
     */
    @ExcelProperty(value = "消费金额",converter = LongStringConverter.class)
    @Column(name = "trans_total_amount")
    private Long transTotalAmount;

    /**
     * 消费收益
     */
    @ExcelProperty(value = "消费收益",converter = LongStringConverter.class)
    @Column(name = "trans_profit_amount")
    private Long transProfitAmount;

    /**
     * 结算笔数
     */
    @ExcelProperty(value = "结算笔数",converter = LongStringConverter.class)
    @Column(name = "df_total_count")
    private Long dfTotalCount;

    /**
     * 结算金额
     */
    @ExcelProperty(value = "结算金额",converter = LongStringConverter.class)
    @Column(name = "df_total_amount")
    private Long dfTotalAmount;

    /**
     * 结算收益
     */
    @ExcelProperty(value = "结算收益",converter = LongStringConverter.class)
    @Column(name = "df_profit_amount")
    private Long dfProfitAmount;

    /**
     * 退款笔数
     */
    @ExcelProperty(value = "退款笔数",converter = LongStringConverter.class)
    @Column(name = "rf_total_count")
    private Long rfTotalCount;

    /**
     * 退款金额
     */
    @ExcelProperty(value = "退款金额",converter = LongStringConverter.class)
    @Column(name = "rf_total_amount")
    private Long rfTotalAmount;

    /**
     * 退款收益
     */
    @ExcelProperty(value = "退款收益",converter = LongStringConverter.class)
    @Column(name = "rf_profit_amount")
    private Long rfProfitAmount;

    /**
     * 退票笔数
     */
    @ExcelProperty(value = "退票笔数",converter = LongStringConverter.class)
    @Column(name = "return_ticket_count")
    private Long returnTicketCount;

    /**
     * 退票金额
     */
    @ExcelProperty(value = "退票金额",converter = LongStringConverter.class)
    @Column(name = "return_ticket_amount")
    private Long returnTicketAmount;

    /**
     * 退票收益
     */
    @ExcelProperty(value = "退票收益",converter = LongStringConverter.class)
    @Column(name = "return_ticket_profit_amount")
    private Long returnTicketProfitAmount;

    /**
     * 合作方收益
     */
    @ExcelProperty(value = "合作方收益",converter = LongStringConverter.class)
    @Column(name = "total_profit_amount")
    private Long totalProfitAmount;

    /**
     * 入账流水号
     */
    @ExcelProperty(value = "入账流水号")
    @Column(name = "account_in_id")
    private String accountInId;

    /**
     * 分润状态
     */
    @ExcelProperty(value = "分润状态",converter = IntegerStringConverter.class)
    @Column(name = "state")
    private Integer state;

    /**
     * 订单日期
     */
    @ExcelProperty(value = "订单日期")
    @Column(name = "order_id")
    private String orderId;

    /**
     * 提现日期
     */
    @ExcelProperty(value = "提现日期",converter = IntegerStringConverter.class)
    @Column(name = "settle_date")
    private Integer settleDate;

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
