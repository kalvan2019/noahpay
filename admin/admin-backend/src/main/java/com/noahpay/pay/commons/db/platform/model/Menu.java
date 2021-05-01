package com.noahpay.pay.commons.db.platform.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kalvan.admin.annotation.Dict;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单资源实体
 * 表名 menu
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Table(name = "menu")
public class Menu implements Serializable, Comparable<Menu> {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ShardingUk
    @ExcelProperty(value = "id")
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 系统代码
     */
    @ExcelProperty(value = "系统代码")
    @Dict(DictType.SYSTEM_CODE)
    @Column(name = "system_code")
    private String systemCode;

    /**
     * 菜单名
     */
    @ExcelProperty(value = "菜单名")
    @Column(name = "title")
    private String title;

    /**
     * 资源类型
     */
    @ExcelProperty(value = "资源类型")
    @Column(name = "type")
    private String type;

    /**
     * 隐藏子菜单
     */
    @ExcelProperty(value = "隐藏子菜单")
    @Column(name = "hide_children")
    private Integer hideChildren;

    /**
     * 菜单图标
     */
    @ExcelProperty(value = "菜单图标")
    @Column(name = "icon")
    private String icon;

    /**
     * 资源路径
     */
    @ExcelProperty(value = "资源路径")
    @Column(name = "path")
    private String path;

    /**
     * 重定向路径
     */
    @ExcelProperty(value = "重定向路径")
    @Column(name = "redirect")
    private String redirect;

    /**
     * 组件名
     */
    @ExcelProperty(value = "组件名")
    @Column(name = "name")
    private String name;
    /**
     * 权限代码
     */
    @ExcelProperty(value = "权限代码")
    @Column(name = "code")
    private String code;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    @Column(name = "priority")
    private Integer priority;

    /**
     * 父菜单id
     */
    @ExcelProperty(value = "父菜单id")
    @Column(name = "parent_id")
    private Long parentId;

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

    @Transient
    private List<Menu> children;

    @Override
    public int compareTo(Menu o) {
        return this.getPriority() - o.getPriority();
    }
}
