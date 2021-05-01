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
 * 渠道收款商户绑定Import 新增修改数据对象支持excel导入
 * 表名 channel_multi_merchant
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class ChannelMultiMerchantImport implements Serializable {
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
     * 商户号
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "商户号不能为空" )
    private Long merchantNo;
    /**
     * 渠道商户号
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "渠道商户号不能为空" )
    private String channelMerchantNo;
    /**
     * 渠道商户名称
     */
    @ExcelProperty(index = 4)
    @NotBlank(groups = Add.class, message = "渠道商户名称不能为空" )
    private String channelMerchantName;
    /**
     * 备注
     */
    @ExcelProperty(index = 5)
    private String remark;
    /**
     * 状态
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
