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
 * 渠道商户信息Import 新增修改数据对象支持excel导入
 * 表名 channel_merchant_pool
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class ChannelMerchantPoolImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道编号
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "渠道编号不能为空" )
    private Integer channelNo;
    /**
     * 渠道商户号
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "渠道商户号不能为空" )
    private String channelMerchantNo;
    /**
     * 渠道子级商户号
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "渠道子级商户号不能为空" )
    private String channelSubMerchantNo;
    /**
     * 子商户名称
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "子商户名称不能为空" )
    private String channelSubMerchantName;
    /**
     * 商户号
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "商户号不能为空" )
    private Long merchantNo;
    /**
     * 城市代码
     */
    @ExcelProperty(index = 5)
    private Integer city;
    /**
     * 行业类别
     */
    @ExcelProperty(index = 6)
    private Integer mcc;
    /**
     * 开始时间
     */
    @ExcelProperty(index = 7)
    @NotBlank(groups = Add.class, message = "开始时间不能为空" )
    private String beginTime;
    /**
     * 结束时间
     */
    @ExcelProperty(index = 8)
    @NotBlank(groups = Add.class, message = "结束时间不能为空" )
    private String endTime;
    /**
     * 单笔金额下限
     */
    @ExcelProperty(index = 9)
    @NotNull(groups = Add.class, message = "单笔金额下限不能为空" )
    private Long limitMinAmount;
    /**
     * 单笔金额上限
     */
    @ExcelProperty(index = 10)
    @NotNull(groups = Add.class, message = "单笔金额上限不能为空" )
    private Long limitMaxAmount;
    /**
     * 日使用金额
     */
    @ExcelProperty(index = 11)
    @NotNull(groups = Add.class, message = "日使用金额不能为空" )
    private Long dayUseAmount;
    /**
     * 日限额
     */
    @ExcelProperty(index = 12)
    @NotNull(groups = Add.class, message = "日限额不能为空" )
    private Long dayLimitAmount;
    /**
     * 月使用金额
     */
    @ExcelProperty(index = 13)
    @NotNull(groups = Add.class, message = "月使用金额不能为空" )
    private Long monthUseAmount;
    /**
     * 月金额限额
     */
    @ExcelProperty(index = 14)
    @NotNull(groups = Add.class, message = "月金额限额不能为空" )
    private Long monthLimitAmount;
    /**
     * 商户状态
     */
    @ExcelProperty(index = 15)
    @NotNull(groups = Add.class, message = "商户状态不能为空" )
    private Integer state;
    /**
     * 备注
     */
    @ExcelProperty(index = 16)
    @NotBlank(groups = Add.class, message = "备注不能为空" )
    private String remark;

    /**
     * 主键
     */
    private Long id;
}
