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
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Getter
@Setter
public class DictImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典类型
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "字典类型不能为空")
    private String dictType;
    /**
     * 字典类型名
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "字典类型名不能为空")
    private String dictName;
    /**
     * 数据KEY
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "数据KEY不能为空")
    private String dictKey;
    /**
     * 显示内容
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "显示内容不能为空")
    private String dictValue;
    /**
     * 徽标状态
     */
    @ExcelProperty(index = 4)
    private String dictIcon;
    /**
     * select启用
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "select启用不能为空")
    private Integer selectEnable;
    /**
     * 排序
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "排序不能为空")
    private Integer priority;

    /**
     * 主键
     */
    private Integer id;
}
