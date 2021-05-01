package com.noahpay.pay.risk.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 检查黑名单
 *
 * @author chenliang
 */
@Getter
@Setter
public class RiskBlackRequest implements java.io.Serializable {
    /**
     * 调用方关联id
     */
    @NotBlank(message = "关联交易id不能为空")
    private String refTransId;
    /**
     * 客户号
     */
    private Long custNo;
    /**
     * 身份证号
     */
    private String certificateNo;
    /**
     * 银行账号
     */
    private String bankAccountNo;
    /**
     * 客户邮箱
     */
    private String email;
    /**
     * 客户手机号
     */
    private String mobile;
    /**
     * 营业执照注册号
     */
    private String businessLicenseNo;

}
