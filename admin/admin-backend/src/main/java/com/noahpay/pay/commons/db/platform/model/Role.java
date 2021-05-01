package com.noahpay.pay.commons.db.platform.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
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
 * 角色实体
 * 表名 role
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Getter
@Setter
@Table(name = "role")
public class Role implements Serializable {
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
     * 角色名
     */
    @ExcelProperty(value = "角色名")
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色描述
     */
    @ExcelProperty(value = "角色描述")
    @Column(name = "role_desc")
    private String roleDesc;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
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
    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    /**
     * 角色 -- 权限关系：多对多关系;
     */
    private List<Menu> menuList;

    @Transient
    /**
     * 用户 - 角色关系定义; 一个角色对应多个用户
     */
    private List<Admin> adminList;
    /**
     * 授权使用
     */
    @Transient
    private Long[] menuId;
}
