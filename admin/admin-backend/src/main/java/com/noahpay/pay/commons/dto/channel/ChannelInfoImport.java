package com.noahpay.pay.commons.dto.channel;

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
 * 渠道列表Import 新增修改数据对象支持excel导入
 * 表名 channel_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class ChannelInfoImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道编号
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "渠道编号不能为空" )
    private Integer channelNo;
    /**
     * 渠道名称
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "渠道名称不能为空" )
    private String channelName;
    /**
     * 实现类名
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "实现类名不能为空" )
    private String channelClass;
    /**
     * 渠道商户号
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "渠道商户号不能为空" )
    private String channelMerchantNo;
    /**
     * 大商户轮询
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "大商户轮询不能为空" )
    private Integer merchantPoolConvert;
    /**
     * 转换多商户
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "转换多商户不能为空" )
    private Integer multiMerchantConvert;
    /**
     * 转换发往银行流水号
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "转换发往银行流水号不能为空" )
    private Integer sendSnConvert;
    /**
     * 转换行别
     */
    @ExcelProperty(index = 7)
    @NotNull(groups = Add.class, message = "转换行别不能为空" )
    private Integer bankTypeConvert;
    /**
     * 转换银行账户类型
     */
    @ExcelProperty(index = 8)
    @NotNull(groups = Add.class, message = "转换银行账户类型不能为空" )
    private Integer bankAccountTypeConvert;
    /**
     * 转换证件类型
     */
    @ExcelProperty(index = 9)
    @NotNull(groups = Add.class, message = "转换证件类型不能为空" )
    private Integer certificateTypeConvert;
    /**
     * 转换城市代码
     */
    @ExcelProperty(index = 10)
    @NotNull(groups = Add.class, message = "转换城市代码不能为空" )
    private Integer cityConvert;
    /**
     * 行业类目转换
     */
    @ExcelProperty(index = 11)
    @NotNull(groups = Add.class, message = "行业类目转换不能为空" )
    private Integer mccConvert;
    /**
     * 转换客户号类型
     */
    @ExcelProperty(index = 12)
    @NotNull(groups = Add.class, message = "转换客户号类型不能为空" )
    private Integer custConvert;
    /**
     * 连接超时时间
     */
    @ExcelProperty(index = 13)
    @NotNull(groups = Add.class, message = "连接超时时间不能为空" )
    private Integer connectionTimeout;
    /**
     * 渠道最大并发数
     */
    @ExcelProperty(index = 14)
    @NotNull(groups = Add.class, message = "渠道最大并发数不能为空" )
    private Integer connectionMaxNum;
    /**
     * 读超时时间
     */
    @ExcelProperty(index = 15)
    @NotNull(groups = Add.class, message = "读超时时间不能为空" )
    private Integer readTimeout;
    /**
     * 渠道发送短信
     */
    @ExcelProperty(index = 16)
    @NotNull(groups = Add.class, message = "渠道发送短信不能为空" )
    private Integer smsSupport;
    /**
     * 支持对账 0-支持对账;1-不支持
     */
    @ExcelProperty(index = 17)
    @NotNull(groups = Add.class, message = "支持对账 0-支持对账;1-不支持不能为空" )
    private Integer checkSupport;
    /**
     * 对账时间
     */
    @ExcelProperty(index = 18)
    @NotBlank(groups = Add.class, message = "对账时间不能为空" )
    private String checkTime;
    /**
     * 对账凭证
     */
    @ExcelProperty(index = 19)
    @NotNull(groups = Add.class, message = "对账凭证不能为空" )
    private Integer checkField;
    /**
     * 结算周期
     */
    @ExcelProperty(index = 20)
    @NotBlank(groups = Add.class, message = "结算周期不能为空" )
    private String settlementTime;
    /**
     * 结算账号
     */
    @ExcelProperty(index = 21)
    @NotBlank(groups = Add.class, message = "结算账号不能为空" )
    private String settlementBankAccountNo;
    /**
     * 结算户名
     */
    @ExcelProperty(index = 22)
    @NotBlank(groups = Add.class, message = "结算户名不能为空" )
    private String settlementBankAccountName;
    /**
     * 结算银行
     */
    @ExcelProperty(index = 23)
    @NotBlank(groups = Add.class, message = "结算银行不能为空" )
    private String settlementBankType;
    /**
     * 状态
     */
    @ExcelProperty(index = 24)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
