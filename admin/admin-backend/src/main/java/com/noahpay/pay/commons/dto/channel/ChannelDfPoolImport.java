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
 * 渠道结算商户绑定Import 新增修改数据对象支持excel导入
 * 表名 channel_df_pool
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class ChannelDfPoolImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道编号
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "渠道编号不能为空" )
    private Integer channelNo;
    /**
     * 商户号
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "商户号不能为空" )
    private Long merchantNo;
    /**
     * 渠道商户号
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "渠道商户号不能为空" )
    private String channelMerchantNo;
    /**
     * 渠道商户名称
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "渠道商户名称不能为空" )
    private String channelMerchantName;
    /**
     * 总余额
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "总余额不能为空" )
    private Long amount;
    /**
     * 冻结金额
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "冻结金额不能为空" )
    private Long freezeAmount;
    /**
     * 可用金额
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "可用金额不能为空" )
    private Long availableAmount;
    /**
     * 状态
     */
    @ExcelProperty(index = 7)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;
    /**
     * 备注
     */
    @ExcelProperty(index = 8)
    @NotBlank(groups = Add.class, message = "备注不能为空" )
    private String remark;

    /**
     * 主键
     */
    private Long id;
}
