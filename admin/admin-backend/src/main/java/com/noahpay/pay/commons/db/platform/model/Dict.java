package com.noahpay.pay.commons.db.platform.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
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
 * 数据字典配置实体
 * 表名 dict
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Getter
@Setter
@Table(name = "dict")
public class Dict implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "自增id",converter = LongStringConverter.class)
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 系统代码
     */
    @com.kalvan.admin.annotation.Dict(DictType.SYSTEM_CODE)
    @Column(name = "system_code")
    private String systemCode;

    /**
     * 字典类型
     */
    @ExcelProperty(value = "字典类型")
    @Column(name = "dict_type")
    private String dictType;

    /**
     * 字典类型名
     */
    @ExcelProperty(value = "字典类型名")
    @Column(name = "dict_name")
    private String dictName;

    /**
     * 数据KEY
     */
    @ExcelProperty(value = "数据KEY")
    @Column(name = "dict_key")
    private String dictKey;

    /**
     * 显示内容
     */
    @ExcelProperty(value = "显示内容")
    @Column(name = "dict_value")
    private String dictValue;

    /**
     * 徽标状态
     */
    @ExcelProperty(value = "徽标状态")
    @com.kalvan.admin.annotation.Dict(DictType.DICT_ICON)
    @Column(name = "dict_icon")
    private String dictIcon;

    /**
     * select启用
     */
    @ExcelProperty(value = "select启用", converter = IntegerStringConverter.class)
    @com.kalvan.admin.annotation.Dict(DictType.SWITCH_STATE)
    @Column(name = "select_enable")
    private Integer selectEnable;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序", converter = IntegerStringConverter.class)
    @Column(name = "priority")
    private Integer priority;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

}
