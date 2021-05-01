package com.noahpay.pay.commons.dto.platform;

import com.alibaba.excel.annotation.ExcelProperty;
import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数据字典配置Import 新增修改数据对象支持excel导入
 * 表名 dict
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class AppImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 应用代码
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "应用代码不能为空" )
    private String dictKey;
    /**
     * 应用名称
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "应用名称不能为空" )
    private String dictValue;
    /**
     * 排序
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "排序不能为空" )
    private Integer priority;

    /**
     * 主键
     */
    private Integer id;
}
