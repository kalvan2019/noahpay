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
 * 渠道支持支付方式Import 新增修改数据对象支持excel导入
 * 表名 channel_support_pay_type
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class ChannelSupportPayTypeImport implements Serializable {
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
     * 日使用金额
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "日使用金额不能为空" )
    private Long dayUseAmount;
    /**
     * 日限额
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "日限额不能为空" )
    private Long dayLimitAmount;
    /**
     * 日使用笔数
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "日使用笔数不能为空" )
    private Long dayUseNumber;
    /**
     * 日成功交易笔数限制
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "日成功交易笔数限制不能为空" )
    private Long dayLimitNumber;
    /**
     * 月使用金额
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "月使用金额不能为空" )
    private Long monthUseAmount;
    /**
     * 月金额限额
     */
    @ExcelProperty(index = 7)
    @NotNull(groups = Add.class, message = "月金额限额不能为空" )
    private Long monthLimitAmount;
    /**
     * 最后统计日期
     */
    @ExcelProperty(index = 8)
    @NotNull(groups = Add.class, message = "最后统计日期不能为空" )
    private Integer lastDate;
    /**
     * 支持非工作日
     */
    @ExcelProperty(index = 9)
    @NotNull(groups = Add.class, message = "支持非工作日不能为空" )
    private Integer holidaySupport;
    /**
     * 银行路由
     */
    @ExcelProperty(index = 10)
    @NotNull(groups = Add.class, message = "银行路由不能为空" )
    private Integer bankRouteEnabled;
    /**
     * 状态
     */
    @ExcelProperty(index = 11)
    @NotNull(groups = Add.class, message = "启用状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
