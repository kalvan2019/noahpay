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
 * 路由规则配置Import 新增修改数据对象支持excel导入
 * 表名 route_rule
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class RouteRuleImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 路由规则
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "路由规则不能为空" )
    private String routeRule;
    /**
     * 渠道编号
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "渠道编号不能为空" )
    private Integer channelNo;
    /**
     * 行别
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "行别不能为空" )
    private String bankType;
    /**
     * 银行帐户类型
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "银行帐户类型不能为空" )
    private Integer bankAccountType;
    /**
     * 单笔金额下限
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "单笔金额下限不能为空" )
    private Long limitMinAmount;
    /**
     * 单笔金额上限
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "单笔金额上限不能为空" )
    private Long limitMaxAmount;
    /**
     * 开始日期
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "开始日期不能为空" )
    private Integer beginDate;
    /**
     * 结束日期
     */
    @ExcelProperty(index = 7)
    @NotNull(groups = Add.class, message = "结束日期不能为空" )
    private Integer endDate;
    /**
     * 开始时间
     */
    @ExcelProperty(index = 8)
    @NotBlank(groups = Add.class, message = "开始时间不能为空" )
    private String beginTime;
    /**
     * 结束时间
     */
    @ExcelProperty(index = 9)
    @NotBlank(groups = Add.class, message = "结束时间不能为空" )
    private String endTime;
    /**
     * 优先级
     */
    @ExcelProperty(index = 10)
    @NotNull(groups = Add.class, message = "优先级不能为空" )
    private Integer priority;
    /**
     * 权重因子
     */
    @ExcelProperty(index = 11)
    @NotNull(groups = Add.class, message = "权重因子不能为空" )
    private Integer weight;
    /**
     * 备注
     */
    @ExcelProperty(index = 12)
    @NotBlank(groups = Add.class, message = "备注不能为空" )
    private String remark;
    /**
     * 状态
     */
    @ExcelProperty(index = 13)
    @NotNull(groups = Add.class, message = "启用状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
