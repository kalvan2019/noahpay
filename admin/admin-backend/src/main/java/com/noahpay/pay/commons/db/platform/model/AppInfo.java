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
 * app信息实体
 * 表名 app_info
 *
 * @author kalvan.tools:kalvan
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@Table(name = "app_info")
public class AppInfo implements Serializable {
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
     * app名称
     */
    @ExcelProperty(value = "app名称")
    @Column(name = "app_name")
    private String appName;

    /**
     * app分组
     */
    @ExcelProperty(value = "app分组")
    @Dict(DictType.GROUP_NAME)
    @Column(name = "group_name")
    private String groupName;

    /**
     * 绑定ip
     */
    @ExcelProperty(value = "绑定ip")
    @Column(name = "bind_ip")
    private String bindIp;

    /**
     * 绑定域名
     */
    @ExcelProperty(value = "绑定域名")
    @Column(name = "bind_domain")
    private String bindDomain;

    /**
     * 绑定密钥
     */
    @ExcelProperty(value = "绑定密钥")
    @Column(name = "bind_key")
    private String bindKey;

    /**
     * app状态
     */
    @ExcelProperty(value = "app状态",converter = IntegerStringConverter.class)
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
