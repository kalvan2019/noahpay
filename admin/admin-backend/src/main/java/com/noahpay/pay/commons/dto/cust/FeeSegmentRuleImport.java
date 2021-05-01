package com.noahpay.pay.commons.dto.cust;

import com.alibaba.excel.annotation.ExcelProperty;
import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分段计费规则配置Import 新增修改数据对象支持excel导入
 * 表名 fee_segment_rule
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class FeeSegmentRuleImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分段计费规则
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "分段计费规则不能为空" )
    private String feeSegmentRule;
    /**
     * 开始金额
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "开始金额不能为空" )
    private Long beginAmount;
    /**
     * 结束金额
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "结束金额不能为空" )
    private Long endAmount;
    /**
     * 计费方法
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "计费方法不能为空" )
    private Integer feeType;
    /**
     * 费率
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "费率不能为空" )
    private Long feeRate;
    /**
     * 固定收费
     */
    @ExcelProperty(index = 5)
    private Long fixFee;
    /**
     * 状态
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
