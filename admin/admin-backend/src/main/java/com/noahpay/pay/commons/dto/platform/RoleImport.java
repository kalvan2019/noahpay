package com.noahpay.pay.commons.dto.platform;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 角色Import 新增修改数据对象支持excel导入
 * 表名 s_role
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Getter
@Setter
public class RoleImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名
     */
    @ExcelProperty(index = 0)
    @NotBlank(message = "角色名不能为空")
    private String roleName;

    /**
     * 角色描述
     */
    @ExcelProperty(index = 1)
    @NotBlank(message = "角色描述不能为空")
    private String roleDesc;

    /**
     * 状态
     */
    @ExcelProperty(index = 2)
    @NotNull(message = "状态不能为空")
    private Integer state;

}
