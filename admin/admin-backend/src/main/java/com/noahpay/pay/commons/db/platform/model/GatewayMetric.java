package com.noahpay.pay.commons.db.platform.model;

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
 * 网关统计实体
 * 表名 gateway_metric
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "gateway_metric")
public class GatewayMetric implements Serializable {
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
     * 报告日期
     */
    @ExcelProperty(value = "报告日期",converter = IntegerStringConverter.class)
    @Column(name = "report_date")
    private Integer reportDate;

    /**
     * appId
     */
    @ExcelProperty(value = "appId")
    @Column(name = "app_id")
    private String appId;

    /**
     * 服务
     */
    @ExcelProperty(value = "服务")
    @Column(name = "service")
    private String service;

    /**
     * 通过qps
     */
    @ExcelProperty(value = "通过qps",converter = IntegerStringConverter.class)
    @Column(name = "pass_qps")
    private Integer passQps;

    /**
     * 限流qps
     */
    @ExcelProperty(value = "限流qps",converter = IntegerStringConverter.class)
    @Column(name = "block_qps")
    private Integer blockQps;

    /**
     * 成功qps
     */
    @ExcelProperty(value = "成功qps",converter = IntegerStringConverter.class)
    @Column(name = "success_qps")
    private Integer successQps;

    /**
     * 异常次数
     */
    @ExcelProperty(value = "异常次数",converter = IntegerStringConverter.class)
    @Column(name = "exception_qps")
    private Integer exceptionQps;

    /**
     * 所有成功qps耗时总和
     */
    @ExcelProperty(value = "所有成功qps耗时总和",converter = LongStringConverter.class)
    @Column(name = "rt")
    private Long rt;

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
