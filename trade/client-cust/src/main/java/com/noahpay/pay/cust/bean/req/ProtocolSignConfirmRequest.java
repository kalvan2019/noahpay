package com.noahpay.pay.cust.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 客户绑卡
 *
 * @author chenliang
 */
@Getter
@Setter
public class ProtocolSignConfirmRequest implements Serializable {
    /**
     * 订单号
     */
    @Size(min = 1, max = 32, message = "订单号长度需在1-32位")
    @NotBlank(message = "订单号不能为空")
    private String orderId;
    /**
     * 银行账户
     */
    @NotBlank(message = "银行账户不可以为空")
    private String bankAccountNo;
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
