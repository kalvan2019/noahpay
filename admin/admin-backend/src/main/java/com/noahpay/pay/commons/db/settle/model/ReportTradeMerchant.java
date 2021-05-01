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
 * 机构交易汇总表实体
 * 表名 report_trade_merchant
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "report_trade_merchant")
public class ReportTradeMerchant implements Serializable {
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
     * 商户号
     */
    @ExcelProperty(value = "商户号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 商户名
     */
    @ExcelProperty(value = "商户名")
    @Column(name = "merchant_name")
    private String merchantName;

    /**
     * 成功交易笔数
     */
    @ExcelProperty(value = "成功交易笔数",converter = LongStringConverter.class)
    @Column(name = "success_count")
    private Long successCount;

    /**
     * 成功交易金额
     */
    @ExcelProperty(value = "成功交易金额",converter = LongStringConverter.class)
    @Column(name = "success_amount")
    private Long successAmount;

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
