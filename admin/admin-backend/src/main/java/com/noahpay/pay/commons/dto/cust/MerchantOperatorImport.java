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
 * 商户操作员表Import 新增修改数据对象支持excel导入
 * 表名 merchant_operator
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class MerchantOperatorImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户号
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "商户号不能为空" )
    private Long merchantNo;
    /**
     * 操作员账号
     */
    @ExcelProperty(index = 1)
    private Long operatorNo;
    /**
     * 操作员姓名
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "操作员姓名不能为空" )
    private String operatorName;
    /**
     * 操作员状态:0.生效；1.冻结；
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "操作员状态:0.生效；1.冻结；不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
