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
 * 商户信息Import 新增修改数据对象支持excel导入
 * 表名 merchant_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class MerchantInfoImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户号
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "商户号不能为空" )
    private Long merchantNo;
    /**
     * 商户名
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "商户名不能为空" )
    private String merchantName;
    /**
     * 商户客户号
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "商户客户号不能为空" )
    private Long custNo;
    /**
     * 营业执照注册号
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "营业执照注册号不能为空" )
    private String businessLicenseNo;
    /**
     * 结算银行帐号
     */
    @ExcelProperty(index = 4)
    @NotBlank(groups = Add.class, message = "结算银行帐号不能为空" )
    private String bankAccountNo;
    /**
     * 银行户名
     */
    @ExcelProperty(index = 5)
    @NotBlank(groups = Add.class, message = "银行户名不能为空" )
    private String bankAccountName;
    /**
     * 银行帐户类型
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "银行帐户类型不能为空" )
    private Integer bankAccountType;
    /**
     * 行别
     */
    @ExcelProperty(index = 7)
    @NotBlank(groups = Add.class, message = "行别不能为空" )
    private String bankType;
    /**
     * 行名
     */
    @ExcelProperty(index = 8)
    @NotBlank(groups = Add.class, message = "行名不能为空" )
    private String bankName;
    /**
     * 商户状态
     */
    @ExcelProperty(index = 9)
    @NotNull(groups = Add.class, message = "商户状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
