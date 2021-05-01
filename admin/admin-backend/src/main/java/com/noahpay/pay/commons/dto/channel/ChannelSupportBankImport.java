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
 * 渠道支持银行Import 新增修改数据对象支持excel导入
 * 表名 channel_support_bank
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class ChannelSupportBankImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道编号
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "渠道编号不能为空" )
    private Integer channelNo;
    /**
     * 支付类型
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "支付类型不能为空" )
    private Integer payType;
    /**
     * 单笔金额下限
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "单笔金额下限不能为空" )
    private Long limitMinAmount;
    /**
     * 单笔金额上限
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "单笔金额上限不能为空" )
    private Long limitMaxAmount;
    /**
     * 开始时间
     */
    @ExcelProperty(index = 4)
    @NotBlank(groups = Add.class, message = "开始时间不能为空" )
    private String beginTime;
    /**
     * 结束时间
     */
    @ExcelProperty(index = 5)
    @NotBlank(groups = Add.class, message = "结束时间不能为空" )
    private String endTime;
    /**
     * 银行帐户类型
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "银行帐户类型不能为空" )
    private Integer bankAccountType;
    /**
     * 行别组
     */
    @ExcelProperty(index = 7)
    @NotBlank(groups = Add.class, message = "行别组不能为空" )
    private String bankTypeGroup;
    /**
     * 地区组
     */
    @ExcelProperty(index = 8)
    @NotBlank(groups = Add.class, message = "地区组不能为空" )
    private String bankCityGroup;
    /**
     * 银行组是/非
     */
    @ExcelProperty(index = 9)
    private Integer bankSupport;
    /**
     * 备注
     */
    @ExcelProperty(index = 10)
    @NotBlank(groups = Add.class, message = "备注不能为空" )
    private String remark;
    /**
     * 启用状态
     */
    @ExcelProperty(index = 11)
    @NotNull(groups = Add.class, message = "启用状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
