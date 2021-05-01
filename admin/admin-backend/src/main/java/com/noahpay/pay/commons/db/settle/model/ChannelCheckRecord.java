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
 * 对账主表实体
 * 表名 channel_check_record
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "channel_check_record")
public class ChannelCheckRecord implements Serializable {
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
     * 渠道号
     */
    @ExcelProperty(value = "渠道号",converter = IntegerStringConverter.class)
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 会计日期
     */
    @ExcelProperty(value = "会计日期",converter = IntegerStringConverter.class)
    @Column(name = "check_date")
    private Integer checkDate;

    /**
     * 对账文件路径
     */
    @ExcelProperty(value = "对账文件路径")
    @Column(name = "file_path")
    private String filePath;

    /**
     * 业务类型
     */
    @ExcelProperty(value = "业务类型",converter = IntegerStringConverter.class)
    @Column(name = "file_type")
    private Integer fileType;

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
     * 支持对账 0-支持对账;1-不支持
     */
    @ExcelProperty(value = "支持对账 0-支持对账;1-不支持",converter = IntegerStringConverter.class)
    @Column(name = "check_support")
    private Integer checkSupport;

    /**
     * 对账凭证
     */
    @ExcelProperty(value = "对账凭证",converter = IntegerStringConverter.class)
    @Column(name = "check_field")
    private Integer checkField;

    /**
     * 对账时间点
     */
    @ExcelProperty(value = "对账时间点",converter = IntegerStringConverter.class)
    @Column(name = "check_time")
    private Integer checkTime;

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
