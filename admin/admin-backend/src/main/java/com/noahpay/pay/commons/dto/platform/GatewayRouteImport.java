package com.noahpay.pay.commons.dto.platform;

import com.alibaba.excel.annotation.ExcelProperty;
import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 网关路由表Import 新增修改数据对象支持excel导入
 * 表名 gateway_route
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class GatewayRouteImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 路由id
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "路由id不能为空" )
    private String routeId;
    /**
     * uri路径
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "uri路径不能为空" )
    private String uri;
    /**
     * 判定器
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "判定器不能为空" )
    private String predicates;
    /**
     * 过滤器
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "过滤器不能为空" )
    private String filters;
    /**
     * 优先级
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "优先级不能为空" )
    private Integer orders;
    /**
     * 描述
     */
    @ExcelProperty(index = 5)
    @NotBlank(groups = Add.class, message = "描述不能为空" )
    private String description;
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
