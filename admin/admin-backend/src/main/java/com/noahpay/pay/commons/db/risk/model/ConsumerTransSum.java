package com.noahpay.pay.commons.db.risk.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.admin.annotation.NumberConvert;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import com.kalvan.sensitive.annotation.Desensitized;
import com.kalvan.sensitive.enums.SensitiveType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 客户交易额度累计实体
 * 表名 consumer_trans_sum
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "consumer_trans_sum")
public class ConsumerTransSum implements Serializable {
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
     * 交易日期
     */
    @ExcelProperty(value = "交易日期",converter = IntegerStringConverter.class)
    @Column(name = "trans_date")
    private Integer transDate;

    /**
     * 商户号
     */
    @ExcelProperty(value = "商户号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 交易类型
     */
    @ExcelProperty(value = "交易类型",converter = IntegerStringConverter.class)
    @Dict(DictType.TRANS_TYPE)
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 银行帐号
     */
    @Desensitized(type = SensitiveType.MOBILE_PHONE)
    @ExcelProperty(value = "银行帐号")
    @Column(name = "bank_account_no")
    private String bankAccountNo;

    /**
     * 证件号码
     */
    @Desensitized(type = SensitiveType.MOBILE_PHONE)
    @ExcelProperty(value = "证件号码")
    @Column(name = "certificate_no")
    private String certificateNo;

    /**
     * 客户手机
     */
    @Desensitized(type = SensitiveType.MOBILE_PHONE)
    @ExcelProperty(value = "客户手机")
    @Column(name = "mobile")
    private String mobile;

    /**
     * 日使用笔数
     */
    @ExcelProperty(value = "日使用笔数",converter = LongStringConverter.class)
    @Column(name = "day_use_number")
    private Long dayUseNumber;

    /**
     * 日使用金额
     */
    @ExcelProperty(value = "日使用金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "day_use_amount")
    private Long dayUseAmount;

    /**
     * 月使用笔数
     */
    @ExcelProperty(value = "月使用笔数",converter = LongStringConverter.class)
    @Column(name = "month_use_number")
    private Long monthUseNumber;

    /**
     * 月使用金额
     */
    @ExcelProperty(value = "月使用金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "month_use_amount")
    private Long monthUseAmount;

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
