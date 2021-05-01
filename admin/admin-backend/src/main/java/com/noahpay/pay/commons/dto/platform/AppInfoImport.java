package com.noahpay.pay.commons.dto.platform;

import com.alibaba.excel.annotation.ExcelProperty;
import com.kalvan.admin.valid.Add;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * app信息Import 新增修改数据对象支持excel导入
 * 表名 app_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class AppInfoImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * appId
     */
    @ExcelProperty(index = 0)
    @NotBlank(groups = Add.class, message = "appId不能为空" )
    private String appId;
    /**
     * app名称
     */
    @ExcelProperty(index = 1)
    @NotBlank(groups = Add.class, message = "app名称不能为空" )
    private String appName;
    /**
     * app分组
     */
    @ExcelProperty(index = 2)
    @NotBlank(groups = Add.class, message = "app分组不能为空" )
    private String groupName;
    /**
     * 绑定ip
     */
    @ExcelProperty(index = 3)
    private String bindIp;
    /**
     * 绑定域名
     */
    @ExcelProperty(index = 4)
    private String bindDomain;
    /**
     * 绑定密钥
     */
    @ExcelProperty(index = 5)
    private String bindKey;
    /**
     * remark
     */
    @ExcelProperty(index = 6)
    private String remark;
    /**
     * app状态
     */
    private Integer state;

    /**
     * 主键
     */
    private Long id;
}
