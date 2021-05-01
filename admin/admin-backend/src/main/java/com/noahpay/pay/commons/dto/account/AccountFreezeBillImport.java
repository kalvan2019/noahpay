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
 * 账户冻结解冻明细Import 新增修改数据对象支持excel导入
 * 表名 account_freeze_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class AccountFreezeBillImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 冻结id
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "冻结id不能为空" )
    private String accountFreezeId;
    /**
     * 关联交易流水
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "关联交易流水不能为空" )
    private String refTransId;
    /**
     * 账户号
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "账户号不能为空" )
    private Long accountNo;
    /**
     * 客户号
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "客户号不能为空" )
    private Long custNo;
    /**
     * 冻结金额
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "冻结金额不能为空" )
    private Long freezeAmount;
    /**
     * 冻结原因
     */
    @ExcelProperty(index = 5)
    @NotBlank(groups = Add.class, message = "冻结原因不能为空" )
    private String freezeReason;
    /**
     * 冻结日期
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "冻结日期不能为空" )
    private Integer freezeAccountDate;
    /**
     * 解冻金额
     */
    @ExcelProperty(index = 7)
    private Long unfreezeAmount;
    /**
     * 解冻原因
     */
    @ExcelProperty(index = 8)
    @NotBlank(groups = Add.class, message = "解冻原因不能为空" )
    private String unfreezeReason;
    /**
     * 解冻日期
     */
    @ExcelProperty(index = 9)
    @NotNull(groups = Add.class, message = "解冻日期不能为空" )
    private Integer unfreezeAccountDate;
    /**
     * 冻结状态
     */
    @ExcelProperty(index = 10)
    @NotNull(groups = Add.class, message = "冻结状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
