package com.noahpay.pay.commons.dto.platform;

import com.alibaba.excel.annotation.ExcelProperty;
import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 灰度服务配置Import 新增修改数据对象支持excel导入
 * 表名 gray_service
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class GrayServiceImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务实例
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "服务实例不能为空" )
    private String serviceId;
    /**
     * ip
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "ip不能为空" )
    private String host;
    /**
     * 端口
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "端口不能为空" )
    private String port;
    /**
     * 规则id
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "规则名不能为空" )
    private String ruleName;
    /**
     * 灰度标签
     */
    @ExcelProperty(index = 4)
    @NotBlank(groups = Add.class, message = "灰度标签不能为空" )
    private String grayTag;
    /**
     * 状态
     */
    @ExcelProperty(index = 5)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
