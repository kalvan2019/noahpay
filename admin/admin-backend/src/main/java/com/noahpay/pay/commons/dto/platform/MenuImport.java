package com.noahpay.pay.commons.dto.platform;

import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 菜单资源Import 新增修改数据对象支持excel导入
 * 表名 s_menu
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Getter
@Setter
public class MenuImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 系统代码
     */
    private String systemCode;

    /**
     * 菜单名
     */
    @NotBlank(groups = {Add.class}, message = "菜单名不能为空")
    private String title;

    /**
     * 组件名
     */
    private String name;

    /**
     * 资源类型
     */
    @NotBlank(groups = {Add.class}, message = "资源类型不能为空")
    private String type;

    /**
     * 隐藏子菜单
     */
    private Integer hideChildren;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 资源路径
     */
    @NotBlank(groups = {Add.class}, message = "资源路径不能为空")
    private String path;

    /**
     * 资源路径
     */
    private String redirect;

    /**
     * 权限代码
     */
    @NotBlank(groups = {Add.class}, message = "权限代码不能为空")
    private String code;

    /**
     * 排序
     */
    @NotNull(groups = {Add.class}, message = "排序不能为空")
    private Integer priority;

    /**
     * 父菜单id
     */
    @NotNull(groups = {Add.class}, message = "父菜单id不能为空")
    private Long parentId;

}
