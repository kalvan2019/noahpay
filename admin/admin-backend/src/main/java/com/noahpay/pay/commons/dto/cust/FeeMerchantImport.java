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
 * 商户计费配置Import 新增修改数据对象支持excel导入
 * 表名 fee_merchant
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class FeeMerchantImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户号
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "商户号不能为空" )
    private Long merchantNo;
    /**
     * 交易类型
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "交易类型不能为空" )
    private Integer transType;
    /**
     * 计费方式
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "计费方式不能为空" )
    private Integer feeMode;
    /**
     * 计费对象
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "计费对象不能为空" )
    private Integer feeObject;
    /**
     * 计费规则
     */
    @ExcelProperty(index = 4)
    @NotBlank(groups = Add.class, message = "计费规则不能为空" )
    private String feeRule;
    /**
     * 状态
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
