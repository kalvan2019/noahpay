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
 * 渠道扩展参数Import 新增修改数据对象支持excel导入
 * 表名 channel_ext_param
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class ChannelExtParamImport implements Serializable {
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
     * 渠道属性
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "渠道属性不能为空" )
    private String paramKey;
    /**
     * 渠道属性值
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "渠道属性值不能为空" )
    private String paramValue;
    /**
     * 备注
     */
    @ExcelProperty(index = 4)
    @NotBlank(groups = Add.class, message = "备注不能为空" )
    private String remark;

    /**
     * 主键
     */
    private Long id;
}
