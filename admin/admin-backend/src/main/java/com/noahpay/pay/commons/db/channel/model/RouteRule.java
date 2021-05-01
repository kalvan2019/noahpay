package com.noahpay.pay.commons.db.channel.model;

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
 * 路由规则配置实体
 * 表名 route_rule
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "route_rule")
public class RouteRule implements Serializable {
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
     * 路由规则
     */
    @ExcelProperty(value = "路由规则")
    @Column(name = "route_rule")
    private String routeRule;

    /**
     * 渠道编号
     */
    @ExcelProperty(value = "渠道编号",converter = IntegerStringConverter.class)
    @Dict(DictType.CHANNEL_NO)
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 行别
     */
    @ExcelProperty(value = "行别")
    @Dict(DictType.BANK_TYPE)
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 银行帐户类型
     */
    @ExcelProperty(value = "银行帐户类型",converter = IntegerStringConverter.class)
    @Dict(DictType.BANK_ACCOUNT_TYPE)
    @Column(name = "bank_account_type")
    private Integer bankAccountType;

    /**
     * 单笔金额下限
     */
    @ExcelProperty(value = "单笔金额下限",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "limit_min_amount")
    private Long limitMinAmount;

    /**
     * 单笔金额上限
     */
    @ExcelProperty(value = "单笔金额上限",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "limit_max_amount")
    private Long limitMaxAmount;

    /**
     * 开始日期
     */
    @ExcelProperty(value = "开始日期",converter = IntegerStringConverter.class)
    @Column(name = "begin_date")
    private Integer beginDate;

    /**
     * 结束日期
     */
    @ExcelProperty(value = "结束日期",converter = IntegerStringConverter.class)
    @Column(name = "end_date")
    private Integer endDate;

    /**
     * 开始时间
     */
    @ExcelProperty(value = "开始时间")
    @Column(name = "begin_time")
    private String beginTime;

    /**
     * 结束时间
     */
    @ExcelProperty(value = "结束时间")
    @Column(name = "end_time")
    private String endTime;

    /**
     * 优先级
     */
    @ExcelProperty(value = "优先级",converter = IntegerStringConverter.class)
    @Column(name = "priority")
    private Integer priority;

    /**
     * 权重因子
     */
    @ExcelProperty(value = "权重因子",converter = IntegerStringConverter.class)
    @Column(name = "weight")
    private Integer weight;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @Column(name = "remark")
    private String remark;

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
