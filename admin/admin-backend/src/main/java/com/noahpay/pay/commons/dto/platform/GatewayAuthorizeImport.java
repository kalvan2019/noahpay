package com.noahpay.pay.commons.dto.platform;

import com.alibaba.excel.annotation.ExcelProperty;
import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 接入方服务授权表Import 新增修改数据对象支持excel导入
 * 表名 gateway_authorize
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class GatewayAuthorizeImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * appId
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "appId不能为空" )
    private String appId;
    /**
     * 服务
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "服务不能为空" )
    private String service;
    /**
     * 服务状态
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "服务状态不能为空" )
    private Integer state;
    /**
     * 服务访问限制
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "服务访问限制不能为空" )
    private Integer maxAccess;
    /**
     * 时间窗口
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "时间窗口不能为空" )
    private Integer times;

    /**
     * 主键
     */
    private Long id;
}
