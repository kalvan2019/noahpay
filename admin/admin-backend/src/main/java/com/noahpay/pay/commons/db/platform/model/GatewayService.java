package com.noahpay.pay.commons.db.platform.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 服务发布实体
 * 表名 gateway_service
 *
 * @author kalvan.tools:kalvan
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@Table(name = "gateway_service")
public class GatewayService implements Serializable {
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
     * 服务
     */
    @ExcelProperty(value = "服务")
    @Column(name = "service")
    private String service;

    /**
     * 服务名
     */
    @ExcelProperty(value = "服务名")
    @Column(name = "service_name")
    private String serviceName;

    /**
     * 服务分组
     */
    @ExcelProperty(value = "服务分组")
    @Dict(DictType.GROUP_NAME)
    @Column(name = "group_name")
    private String groupName;

    /**
     * 资源名称
     */
    @ExcelProperty(value = "资源名称")
    @Column(name = "resource")
    private String resource;

    /**
     * 路由id
     */
    @ExcelProperty(value = "路由id")
    @Column(name = "route_id")
    private String routeId;

    /**
     * 服务状态
     */
    @ExcelProperty(value = "服务状态",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "state")
    private Integer state;

    /**
     * 服务访问限制
     */
    @ExcelProperty(value = "服务访问限制",converter = IntegerStringConverter.class)
    @Column(name = "max_access")
    private Integer maxAccess;

    /**
     * 时间窗口
     */
    @ExcelProperty(value = "时间窗口",converter = IntegerStringConverter.class)
    @Column(name = "times")
    private Integer times;

    /**
     * 优先级
     */
    @ExcelProperty(value = "优先级",converter = IntegerStringConverter.class)
    @Column(name = "priority")
    private Integer priority;

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
