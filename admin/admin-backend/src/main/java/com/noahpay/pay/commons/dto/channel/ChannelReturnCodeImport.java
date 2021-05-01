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
 * 渠道返回码Import 新增修改数据对象支持excel导入
 * 表名 channel_return_code
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class ChannelReturnCodeImport implements Serializable {
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
     * 渠道返回码
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "渠道返回码不能为空" )
    private String channelReturnCode;
    /**
     * 渠道返回备注
     */
    @ExcelProperty(index = 3)
    private String channelResultNote;
    /**
     * 转换返回码
     */
    @ExcelProperty(index = 4)
    @NotBlank(groups = Add.class, message = "转换返回码不能为空" )
    private String returnCode;
    /**
     * 转换返回说明
     */
    @ExcelProperty(index = 5)
    private String returnNote;
    /**
     * 交易状态
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "交易状态不能为空" )
    private Integer transState;

    /**
     * 主键
     */
    private Long id;
}
