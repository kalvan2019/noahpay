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
 * 商户交易业务入网Import 新增修改数据对象支持excel导入
 * 表名 merchant_product_trans
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class MerchantProductTransImport implements Serializable {
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
     * 单笔金额上限
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "单笔金额上限不能为空" )
    private Long limitMaxAmount;
    /**
     * 日限额
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "日限额不能为空" )
    private Long dayLimitAmount;
    /**
     * 日限笔数
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "日限笔数不能为空" )
    private Long dayLimitNumber;
    /**
     * 月金额限额
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "月金额限额不能为空" )
    private Long monthLimitAmount;
    /**
     * 月笔数限额
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "月笔数限额不能为空" )
    private Long monthLimitNumber;
    /**
     * 生效日期
     */
    @ExcelProperty(index = 7)
    @NotNull(groups = Add.class, message = "生效日期不能为空" )
    private Integer effectiveDate;
    /**
     * 失效日期
     */
    @ExcelProperty(index = 8)
    @NotNull(groups = Add.class, message = "失效日期不能为空" )
    private Integer expiryDate;
    /**
     * 客户协议审核
     */
    @ExcelProperty(index = 9)
    @NotNull(groups = Add.class, message = "客户协议审核不能为空" )
    private Integer signAuditType;
    /**
     * 签约验证手机
     */
    @ExcelProperty(index = 10)
    @NotNull(groups = Add.class, message = "签约验证手机不能为空" )
    private Integer signCheckSms;
    /**
     * 签约成功发短信
     */
    @ExcelProperty(index = 11)
    @NotNull(groups = Add.class, message = "签约成功发短信不能为空" )
    private Integer signSendSms;
    /**
     * 处理方式
     */
    @ExcelProperty(index = 12)
    @NotNull(groups = Add.class, message = "处理方式不能为空" )
    private Integer transDealType;
    /**
     * 交易审核
     */
    @ExcelProperty(index = 13)
    @NotNull(groups = Add.class, message = "交易审核不能为空" )
    private Integer transAuditType;
    /**
     * 交易检查客户协议
     */
    @ExcelProperty(index = 14)
    @NotNull(groups = Add.class, message = "交易检查客户协议不能为空" )
    private Integer transCheckProtocol;
    /**
     * 交易验证手机
     */
    @ExcelProperty(index = 15)
    @NotNull(groups = Add.class, message = "交易验证手机不能为空" )
    private Integer transCheckSms;
    /**
     * 交易成功发短信
     */
    @ExcelProperty(index = 16)
    @NotNull(groups = Add.class, message = "交易成功发短信不能为空" )
    private Integer transSendSms;
    /**
     * 入网状态
     */
    @ExcelProperty(index = 17)
    @NotNull(groups = Add.class, message = "入网状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
