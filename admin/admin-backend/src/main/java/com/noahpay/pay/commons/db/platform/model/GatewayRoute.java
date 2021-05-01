package com.noahpay.pay.commons.db.platform.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.kalvan.admin.annotation.Dict;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 网关路由表实体
 * 表名 gateway_route
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "gateway_route")
public class GatewayRoute implements Serializable {
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
     * 路由id
     */
    @ExcelProperty(value = "路由id")
    @Column(name = "route_id")
    private String routeId;

    /**
     * uri路径
     */
    @ExcelProperty(value = "uri路径")
    @Column(name = "uri")
    private String uri;

    /**
     * 判定器
     */
    @ExcelProperty(value = "判定器")
    @Column(name = "predicates")
    private String predicates;

    /**
     * 过滤器
     */
    @ExcelProperty(value = "过滤器")
    @Column(name = "filters")
    private String filters;

    /**
     * 优先级
     */
    @ExcelProperty(value = "优先级",converter = IntegerStringConverter.class)
    @Column(name = "orders")
    private Integer orders;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    @Column(name = "description")
    private String description;

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
