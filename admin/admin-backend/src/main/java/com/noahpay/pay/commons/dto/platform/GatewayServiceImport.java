package com.noahpay.pay.commons.dto.platform;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 服务发布Import 新增修改数据对象支持excel导入
 * 表名 gateway_service
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class GatewayServiceImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "服务不能为空" )
    private String service;
    /**
     * 服务名
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "服务名不能为空" )
    private String serviceName;
    /**
     * 服务分组
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "服务分组不能为空" )
    private String groupName;
    /**
     * 资源名称
     */
    @ExcelProperty(index = 3)
    private String resource;
    /**
     * 路由id
     */
    @ExcelProperty(index = 4)
    private String routeId;
    /**
     * 服务状态
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "服务状态不能为空" )
    private Integer state;
    /**
     * 服务访问限制
     */
    @ExcelProperty(index = 6)
    @NotNull(groups = Add.class, message = "服务访问限制不能为空" )
    private Integer maxAccess;
    /**
     * 时间窗口
     */
    @ExcelProperty(index = 7)
    @NotNull(groups = Add.class, message = "时间窗口不能为空" )
    private Integer times;
    /**
     * 优先级
     */
    @ExcelProperty(value = "优先级",converter = IntegerStringConverter.class)
    private Integer priority;
    /**
     * 主键
     */
    private Long id;
}
