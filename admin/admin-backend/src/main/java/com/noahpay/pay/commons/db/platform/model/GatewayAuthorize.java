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
 * 接入方服务授权表实体
 * 表名 gateway_authorize
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "gateway_authorize")
public class GatewayAuthorize implements Serializable {
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
