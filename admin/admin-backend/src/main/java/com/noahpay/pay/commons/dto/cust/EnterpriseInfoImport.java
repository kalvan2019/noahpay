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
 * 企业客户信息Import 新增修改数据对象支持excel导入
 * 表名 enterprise_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class EnterpriseInfoImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 营业执照注册号
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "营业执照注册号不能为空" )
    private String businessLicenseNo;
    /**
     * 经营范围
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "经营范围不能为空" )
    private String businessScope;
    /**
     * 年营业额
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "年营业额不能为空" )
    private Long businessAmount;
    /**
     * 营业执照有效期
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "营业执照有效期不能为空" )
    private Integer businessLicenseExpiry;
    /**
     * 企业名
     */
    @ExcelProperty(index = 4)
    @NotBlank(groups = Add.class, message = "企业名不能为空" )
    private String companyName;
    /**
     * 营业执照所在地
     */
    @ExcelProperty(index = 5)
    @NotBlank(groups = Add.class, message = "营业执照所在地不能为空" )
    private String companyAddress;
    /**
     * 行业
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "行业不能为空" )
    private Integer industry;
    /**
     * 企业网站
     */
    @ExcelProperty(index = 7)
    private String website;
    /**
     * 企业固定电话
     */
    @ExcelProperty(index = 8)
    private String telephone;
    /**
     * 企业状态
     */
    @ExcelProperty(index = 9)
    @NotNull(groups = Add.class, message = "企业状态不能为空" )
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
