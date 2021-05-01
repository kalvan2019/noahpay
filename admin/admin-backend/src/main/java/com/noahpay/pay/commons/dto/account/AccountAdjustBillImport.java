package com.noahpay.pay.commons.dto.account;

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
 * 账户调账流水Import 新增修改数据对象支持excel导入
 * 表名 account_adjust_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class AccountAdjustBillImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 调账流水id
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "调账流水id不能为空" )
    private String adjustTransId;
    /**
     * 交易类型 20-调账
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "交易类型 20-调账不能为空" )
    private Integer transType;
    /**
     * 凭证类型
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "凭证类型不能为空" )
    private Integer voucherType;
    /**
     * 记账流水
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "记账流水不能为空" )
    private String accountTransId;
    /**
     * 会计日期
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "会计日期不能为空" )
    private Integer accountDate;
    /**
     * 账户号
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "账户号不能为空" )
    private Long accountNo;
    /**
     * 客户号
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "客户号不能为空" )
    private Long custNo;
    /**
     * 入账金额
     */
    @ExcelProperty(index = 7)
    @NotNull(groups = Add.class, message = "入账金额不能为空" )
    private Long incomeAmount;
    /**
     * 出账金额
     */
    @ExcelProperty(index = 8)
    @NotNull(groups = Add.class, message = "出账金额不能为空" )
    private Long outgoAmount;
    /**
     * 调账原因
     */
    @ExcelProperty(index = 9)
    @NotBlank(groups = Add.class, message = "调账原因不能为空" )
    private String adjustReason;
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
