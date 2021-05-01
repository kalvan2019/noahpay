package com.noahpay.pay.commons.dto.platform;

import com.alibaba.excel.annotation.ExcelProperty;
import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 管理员Import 新增修改数据对象支持excel导入
 * 表名 s_admin
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Getter
@Setter
public class AdminImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 登录账号
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "登录账号不能为空")
    private String userCode;

    /**
     * 管理员名称
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "管理员名称不能为空")
    private String userName;

    /**
     * 密码
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "密码不能为空")
    private String password;

    /**
     * 手机号码
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "手机号码不能为空")
    private String mobile;

    /**
     * 邮箱地址
     */
    @ExcelProperty(index = 4)
    @NotBlank(groups = Add.class, message = "邮箱地址不能为空")
    private String email;

    /**
     * 状态
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "状态不能为空")
    private Integer state;

    /**
     * 角色id
     */
    @Transient
    private Long[] roleId;
}
