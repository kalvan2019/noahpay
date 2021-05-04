package com.noahpay.pay.cust.bean.req;

import com.noahpay.pay.cust.bean.model.BankCardInfo;
import com.noahpay.pay.cust.bean.model.CertificateInfo;
import com.noahpay.pay.cust.bean.model.CompanyInfo;
import com.noahpay.pay.enums.regexp.RegExpEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 合作方开通
 *
 * @author chenliang
 */
@Getter
@Setter
public class PartnerRegisterRequest implements Serializable {
    /**
     * 合作方名称
     */
    @NotBlank(message = "合作方名称不可以为空")
    private String partnerName;
    /**
     * 注册手机
     */
    @NotBlank(message = "注册手机不可以为空")
    @Pattern(regexp = RegExpEnum.MOBILE_PHONE)
    private String mobile;
    /**
     * 联系邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 证件信息
     */
    @Valid
    private CertificateInfo certificateInfo;
    /**
     * 结算卡信息
     */
    @Valid
    private BankCardInfo bankCardInfo;
    /**
     * 公司信息
     */
    @Valid
    private CompanyInfo companyInfo;
}
