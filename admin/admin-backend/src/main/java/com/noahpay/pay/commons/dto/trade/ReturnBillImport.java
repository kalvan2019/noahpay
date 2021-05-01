package com.noahpay.pay.commons.dto.trade;

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
 * 退票流水Import 新增修改数据对象支持excel导入
 * 表名 return_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class ReturnBillImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 交易流水号
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "交易流水号不能为空" )
    private String transId;
    /**
     * 交易日期
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "交易日期不能为空" )
    private Integer transDate;
    /**
     * 订单号
     */
    @ExcelProperty(index = 2)
    private String orderId;
    /**
     * 订单金额
     */
    @ExcelProperty(index = 3)
    private Long orderAmount;
    /**
     * 商户号
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "商户号不能为空" )
    private Long merchantNo;
    /**
     * 商户手续费
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "商户手续费不能为空" )
    private Long merchantFee;
    /**
     * 子商户号
     */
    @ExcelProperty(index = 6)
    private Long subMerchantNo;
    /**
     * 子商户手续费
     */
    @ExcelProperty(index = 7)
    private Long subMerchantFee;
    /**
     * 交易会计日期
     */
    @ExcelProperty(index = 8)
    @NotNull(groups = Add.class, message = "交易会计日期不能为空" )
    private Integer transAccountDate;
    /**
     * 退票日期
     */
    @ExcelProperty(index = 9)
    @NotNull(groups = Add.class, message = "退票日期不能为空" )
    private Integer returnTicketDate;
    /**
     * 退票备注
     */
    @ExcelProperty(index = 10)
    private String remark;

    /**
     * 主键
     */
    private Long id;
}
