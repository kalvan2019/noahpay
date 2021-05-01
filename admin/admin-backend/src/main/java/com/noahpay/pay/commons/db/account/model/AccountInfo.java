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
 * 账户钱包实体
 * 表名 account_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "account_info")
public class AccountInfo implements Serializable {
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
     * 账户号
     */
    @ExcelProperty(value = "账户号",converter = LongStringConverter.class)
    @Column(name = "account_no")
    private Long accountNo;

    /**
     * 账户类型
     */
    @ExcelProperty(value = "账户类型")
    @Dict(DictType.ACCOUNT_TYPE)
    @Column(name = "account_type")
    private String accountType;

    /**
     * 账户名
     */
    @ExcelProperty(value = "账户名")
    @Column(name = "account_name")
    private String accountName;

    /**
     * 关联客户编号
     */
    @ExcelProperty(value = "关联客户编号",converter = LongStringConverter.class)
    @Column(name = "cust_no")
    private Long custNo;

    /**
     * 商户号
     */
    @ExcelProperty(value = "商户号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 总余额
     */
    @ExcelProperty(value = "总余额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "amount")
    private Long amount;

    /**
     * 冻结金额
     */
    @ExcelProperty(value = "冻结金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "freeze_amount")
    private Long freezeAmount;

    /**
     * 可用金额
     */
    @ExcelProperty(value = "可用金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "available_amount")
    private Long availableAmount;

    /**
     * 会计日期
     */
    @ExcelProperty(value = "会计日期",converter = IntegerStringConverter.class)
    @Column(name = "account_date")
    private Integer accountDate;

    /**
     * 上日余额
     */
    @ExcelProperty(value = "上日余额",converter = LongStringConverter.class)
    @Column(name = "last_day_use_amount")
    private Long lastDayUseAmount;

    /**
     * 最后交易时间
     */
    @ExcelProperty(value = "最后交易时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_trans_time")
    private Date lastTransTime;

    /**
     * 账户状态
     */
    @ExcelProperty(value = "账户状态",converter = IntegerStringConverter.class)
    @Column(name = "state")
    private Integer state;

    /**
     * 校验字段
     */
    @ExcelProperty(value = "校验字段")
    @Column(name = "sign")
    private String sign;

    /**
     * 版本号
     */
    @ExcelProperty(value = "版本号",converter = LongStringConverter.class)
    @Column(name = "version")
    private Long version;

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
