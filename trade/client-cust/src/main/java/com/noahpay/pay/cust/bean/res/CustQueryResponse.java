package com.noahpay.pay.cust.bean.res;

import com.noahpay.pay.cust.constant.CustStateEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author chenliang
 */
@Getter
@Setter
public class CustQueryResponse implements Serializable {
    /**
     * 客户编号
     */
    @NotNull
    private Long custNo;
    /**
     * 客户名
     */
    @NotBlank
    private String certificateName;
    /**
     * 注册日期
     */
    @NotNull
    private Integer registerDate;
    /**
     * 状态
     *
     * @see CustStateEnum
     */
    @NotNull
    private Integer state;
}
