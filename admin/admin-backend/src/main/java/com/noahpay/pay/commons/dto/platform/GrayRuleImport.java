package com.noahpay.pay.commons.dto.platform;

import com.alibaba.excel.annotation.ExcelProperty;
import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 灰度规则配置Import 新增修改数据对象支持excel导入
 * 表名 gray_rule
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class GrayRuleImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 规则名
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "规则名不能为空" )
    private String ruleName;
    /**
     * 灰度规则
     */
    @ExcelProperty(index = 1)
    private String rule;
    /**
     * 状态
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
