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
 * 渠道报表实体
 * 表名 report_channel
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "report_channel")
public class ReportChannel implements Serializable {
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
     * 渠道商户号
     */
    @ExcelProperty(value = "渠道商户号")
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 交易日期
     */
    @ExcelProperty(value = "交易日期",converter = IntegerStringConverter.class)
    @Column(name = "trade_date")
    private Integer tradeDate;

    /**
     * 渠道编号
     */
    @ExcelProperty(value = "渠道编号",converter = IntegerStringConverter.class)
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 行别
     */
    @ExcelProperty(value = "行别")
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 渠道附加属性
     */
    @ExcelProperty(value = "渠道附加属性",converter = IntegerStringConverter.class)
    @Column(name = "channel_ext_no")
    private Integer channelExtNo;

    /**
     * 成功金额
     */
    @ExcelProperty(value = "成功金额",converter = LongStringConverter.class)
    @Column(name = "success_amount")
    private Long successAmount;

    /**
     * 成功交易笔数
     */
    @ExcelProperty(value = "成功交易笔数",converter = LongStringConverter.class)
    @Column(name = "success_number")
    private Long successNumber;

    /**
     * 渠道成本总金额
     */
    @ExcelProperty(value = "渠道成本总金额",converter = LongStringConverter.class)
    @Column(name = "channel_cost_amount")
    private Long channelCostAmount;

    /**
     * 商户成本总金额
     */
    @ExcelProperty(value = "商户成本总金额",converter = LongStringConverter.class)
    @Column(name = "merchant_cost_amount")
    private Long merchantCostAmount;

    /**
     * 子商户成本总金额
     */
    @ExcelProperty(value = "子商户成本总金额",converter = LongStringConverter.class)
    @Column(name = "sub_merchant_cost_amount")
    private Long subMerchantCostAmount;

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
