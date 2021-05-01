package com.noahpay.pay.commons.dto.cust;

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
 * 合作方关系维护表Import 新增修改数据对象支持excel导入
 * 表名 partner_relation_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
public class PartnerRelationInfoImport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 合作方编号
     */
    @ExcelProperty(index = 0)
    @NotNull(groups = Add.class, message = "合作方编号不能为空" )
    private Long partnerNo;
    /**
     * 商户编号
     */
    @ExcelProperty(index = 1)
    @NotNull(groups = Add.class, message = "商户编号不能为空" )
    private Long merchantNo;
    /**
     * 交易成本费率
     */
    @ExcelProperty(index = 2)
    @NotNull(groups = Add.class, message = "交易成本费率不能为空" )
    private Long feeRate;
    /**
     * 提现成本手续费
     */
    @ExcelProperty(index = 3)
    @NotNull(groups = Add.class, message = "提现成本手续费不能为空" )
    private Long withdrawFee;
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
