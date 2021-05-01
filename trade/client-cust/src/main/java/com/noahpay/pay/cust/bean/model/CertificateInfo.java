package com.noahpay.pay.cust.bean.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 证件信息
 *
 * @author chenliang
 */
@Getter
@Setter
public class CertificateInfo implements Serializable {
    /**
     * 证件类型
     */
    @NotNull(message = "证件类型不能为空")
    private Integer certificateType;
    /**
     * 证件号
     */
    @NotBlank(message = "证件号不能为空")
    private String certificateNo;
    /**
     * 证件姓名
     */
    @NotBlank(message = "证件姓名不能为空")
    private String certificateName;
    /**
     * 证件有效期
     * yyyyMMdd
     */
    @NotNull(message = "证件有效期不能为空")
    private Integer certificateExpiry;
    /**
     * 证件正面照片
     */
    private String certificateFrontPic;
    /**
     * 证件反面照片
     */
    private String certificateBackPic;
    /**
     * 证件地址
     */
    private String certificateAddress;
}
