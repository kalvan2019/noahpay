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
 * 银行交易汇总表实体
 * 表名 report_trade_bank
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "report_trade_bank")
public class ReportTradeBank implements Serializable {
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
     * 会计日期
     */
    @ExcelProperty(value = "会计日期",converter = IntegerStringConverter.class)
    @Column(name = "account_date")
    private Integer accountDate;

    /**
     * 行别
     */
    @ExcelProperty(value = "行别")
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 交易占比
     */
    @ExcelProperty(value = "交易占比")
    @Column(name = "ratio")
    private String ratio;

    /**
     * 成功交易金额
     */
    @ExcelProperty(value = "成功交易金额",converter = LongStringConverter.class)
    @Column(name = "success_amount")
    private Long successAmount;

    /**
     * 成功交易笔数
     */
    @ExcelProperty(value = "成功交易笔数",converter = LongStringConverter.class)
    @Column(name = "success_number")
    private Long successNumber;

    /**
     * 渠道编号
     */
    @ExcelProperty(value = "渠道编号",converter = LongStringConverter.class)
    @Column(name = "channel_no")
    private Long channelNo;

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
