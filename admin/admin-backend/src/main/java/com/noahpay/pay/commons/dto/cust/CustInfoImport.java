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
 * 客户信息Import 新增修改数据对象支持excel导入
 * 表名 cust_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class CustInfoImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户号
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "客户号不能为空" )
    private Long custNo;
    /**
     * 证件号码
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "证件号码不能为空" )
    private String certificateNo;
    /**
     * 客户名称
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "客户名称不能为空" )
    private String certificateName;
    /**
     * 证件类型
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "证件类型不能为空" )
    private Integer certificateType;
    /**
     * 证件地址
     */
    @ExcelProperty(index = 4)
    @NotBlank(groups = Add.class, message = "证件地址不能为空" )
    private String certificateAddress;
    /**
     * 证件有效日期
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "证件有效日期不能为空" )
    private Integer certificateExpiry;
    /**
     * 客户邮箱
     */
    @ExcelProperty(index = 6)
    @NotBlank(groups = Add.class, message = "客户邮箱不能为空" )
    private String email;
    /**
     * 客户手机
     */
    @ExcelProperty(index = 7)
    @NotBlank(groups = Add.class, message = "客户手机不能为空" )
    private String mobile;
    /**
     * 详细地址
     */
    @ExcelProperty(index = 8)
    @NotBlank(groups = Add.class, message = "详细地址不能为空" )
    private String address;
    /**
     * 客户状态
     */
    @ExcelProperty(index = 9)
    @NotNull(groups = Add.class, message = "客户状态不能为空" )
    private Integer state;
    /**
     * 销户时间
     */
    @ExcelProperty(index = 10)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    /**
     * 主键
     */
    private Long id;
}
