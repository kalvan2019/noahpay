package com.noahpay.pay.commons.dto.risk;

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
 * 黑名单Import 新增修改数据对象支持excel导入
 * 表名 black_list
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class BlackListImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 黒名单类型
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "黒名单类型不能为空" )
    private Integer blackType;
    /**
     * 客户号
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "客户号不能为空" )
    private Long custNo;
    /**
     * 客户名称
     */
    @ExcelProperty(index = 2)
    private String custName;
    /**
     * 证件号码
     */
    @ExcelProperty(index = 3)
    private String certificateNo;
    /**
     * 银行帐号
     */
    @ExcelProperty(index = 4)
    private String bankAccountNo;
    /**
     * 客户邮箱
     */
    @ExcelProperty(index = 5)
    private String email;
    /**
     * 客户手机
     */
    @ExcelProperty(index = 6)
    private String mobile;
    /**
     * 营业执照注册号
     */
    @ExcelProperty(index = 7)
    private String businessLicenseNo;
    /**
     * 状态
     */
    @ExcelProperty(index = 8)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
