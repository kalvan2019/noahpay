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
 * 渠道支持银行卡类型组Import 新增修改数据对象支持excel导入
 * 表名 channel_support_bank_type_group
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class ChannelSupportBankTypeGroupImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 行别组
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "行别组不能为空" )
    private String bankTypeGroup;
    /**
     * 行别
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "行别不能为空" )
    private String bankType;
    /**
     * 状态
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
