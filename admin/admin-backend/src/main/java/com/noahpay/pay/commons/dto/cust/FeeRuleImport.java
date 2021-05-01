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
 * 计费规则配置Import 新增修改数据对象支持excel导入
 * 表名 fee_rule
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class FeeRuleImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 计费规则
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "计费规则不能为空" )
    private String feeRule;
    /**
     * 渠道编号
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "渠道编号不能为空" )
    private Integer channelNo;
    /**
     * 支付类型
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "支付类型不能为空" )
    private Integer payType;
    /**
     * 行别
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "行别不能为空" )
    private String bankType;
    /**
     * 银行帐户类型
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "银行帐户类型不能为空" )
    private Integer bankAccountType;
    /**
     * 计费方法
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "计费方法不能为空" )
    private Integer feeType;
    /**
     * 费率
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "费率不能为空" )
    private Long feeRate;
    /**
     * 固定收费
     */
    @ExcelProperty(index = 7)
    private Long fixFee;
    /**
     * 最低手续费
     */
    @ExcelProperty(index = 8)
    @NotNull(groups = Add.class, message = "最低手续费不能为空" )
    private Long minFee;
    /**
     * 分段计费规则
     */
    @ExcelProperty(index = 9)
    @NotBlank(groups = Add.class, message = "分段计费规则不能为空" )
    private String feeSegmentRule;
    /**
     * 状态
     */
    @ExcelProperty(index = 10)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
