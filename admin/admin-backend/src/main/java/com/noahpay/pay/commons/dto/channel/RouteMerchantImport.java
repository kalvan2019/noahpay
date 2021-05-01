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
 * 商户路由配置Import 新增修改数据对象支持excel导入
 * 表名 route_merchant
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class RouteMerchantImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户号
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "商户号不能为空" )
    private Long merchantNo;
    /**
     * 支付类型
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "支付类型不能为空" )
    private Integer payType;
    /**
     * 渠道编号
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "渠道编号不能为空" )
    private Integer channelNo;
    /**
     * 路由规则
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "路由规则不能为空" )
    private String routeRule;
    /**
     * 状态
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
