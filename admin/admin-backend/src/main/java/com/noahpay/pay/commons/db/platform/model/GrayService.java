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
 * 灰度服务配置实体
 * 表名 gray_service
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "gray_service")
public class GrayService implements Serializable {
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
     * 服务实例
     */
    @ExcelProperty(value = "服务实例")
    @Column(name = "service_id")
    private String serviceId;

    /**
     * ip
     */
    @ExcelProperty(value = "ip")
    @Column(name = "host")
    private String host;

    /**
     * 端口
     */
    @ExcelProperty(value = "端口")
    @Column(name = "port")
    private String port;

    /**
     * 规则名
     */
    @ExcelProperty(value = "规则名")
    @Column(name = "rule_name")
    private String ruleName;

    /**
     * 灰度标签
     */
    @ExcelProperty(value = "灰度标签")
    @Column(name = "gray_tag")
    private String grayTag;

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
