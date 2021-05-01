package com.noahpay.pay.commons.dto.platform;

import com.alibaba.excel.annotation.ExcelProperty;
import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * mock服务配置Import 新增修改数据对象支持excel导入
 * 表名 mock_service
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class MockServiceImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "自增id不能为空" )
    private Long id;
    /**
     * 系统
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "系统不能为空" )
    private String serviceId;
    /**
     * 服务地址
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "服务地址不能为空" )
    private String service;
    /**
     * mock地址
     */
    @ExcelProperty(index = 3)
    @NotBlank(groups = Add.class, message = "mock地址不能为空" )
    private String mockUrl;
    /**
     * 状态
     */
    @ExcelProperty(index = 4)
    @NotNull(groups = Add.class, message = "状态不能为空" )
    private Integer state;
    /**
     * 描述
     */
    @ExcelProperty(index = 5)
    private String remark;
}
