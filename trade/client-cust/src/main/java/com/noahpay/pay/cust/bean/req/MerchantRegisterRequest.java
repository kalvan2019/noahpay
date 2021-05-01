package com.noahpay.pay.cust.bean.req;

import com.noahpay.pay.cust.bean.model.BankCardInfo;
import com.noahpay.pay.cust.bean.model.CertificateInfo;
import com.noahpay.pay.cust.bean.model.CompanyInfo;
import com.kalvan.enums.regexp.RegExpEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 商户注册
 *
 * @author chenliang
 */
@Getter
@Setter
public class MerchantRegisterRequest implements Serializable {
    /**
     * 商户名
     */
    @NotBlank(message = "商户名不可以为空")
    private String merchantName;
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
     * 法人证件信息
     */
    @Valid
    private CertificateInfo certificateInfo;
    /**
     * 公司信息
     */
    @Valid
    private CompanyInfo companyInfo;
    /**
     * 结算卡信息
     */
    @Valid
    private BankCardInfo bankCardInfo;
}
