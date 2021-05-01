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
 * 下游对账单实体
 * 表名 merchant_check_record
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "merchant_check_record")
public class MerchantCheckRecord implements Serializable {
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
     * 机构号
     */
    @ExcelProperty(value = "机构号",converter = LongStringConverter.class)
    @Column(name = "org_no")
    private Long orgNo;

    /**
     * 会计日期
     */
    @ExcelProperty(value = "会计日期",converter = IntegerStringConverter.class)
    @Column(name = "check_date")
    private Integer checkDate;

    /**
     * 业务类型
     */
    @ExcelProperty(value = "业务类型",converter = IntegerStringConverter.class)
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 对账文件路径
     */
    @ExcelProperty(value = "对账文件路径")
    @Column(name = "file_path")
    private String filePath;

    /**
     * 错误次数
     */
    @ExcelProperty(value = "错误次数",converter = IntegerStringConverter.class)
    @Column(name = "error_count")
    private Integer errorCount;

    /**
     * 对账状态
     */
    @ExcelProperty(value = "对账状态",converter = IntegerStringConverter.class)
    @Column(name = "state")
    private Integer state;

    /**
     * 说明
     */
    @ExcelProperty(value = "说明")
    @Column(name = "note")
    private String note;

    /**
     * 下载token
     */
    @ExcelProperty(value = "下载token")
    @Column(name = "token")
    private String token;

    /**
     * 下载token限制时间
     */
    @ExcelProperty(value = "下载token限制时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "token_expiry")
    private Date tokenExpiry;

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
    @Column(name = "update_time")
    private Date updateTime;

}
