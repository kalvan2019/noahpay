package com.noahpay.pay.cust.bean.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 企业信息
 *
 * @author chenliang
 */
@Getter
@Setter
public class CompanyInfo implements Serializable {
    /**
     * 营业执照号
     */
    private String businessLicenseNo;
    /**
     * 营业执照图片
     */
    private String businessLicensePic;
    /**
     * 营业执照有效期
     * yyyyMMdd格式
     */
    private Integer businessLicenseExpiry;
    /**
     * 经营范围
     */
    private String businessScope;
    /**
     * 年营业额
     * 单位(万)
     */
    private Long businessAmount;
    /**
     * 企业名
     */
    private String companyName;
    /**
     * 企业地址
     */
    private String companyAddress;
    /**
     * 行业
     */
    private Integer industry;
    /**
     * 企业网站
     */
    private String website;
    /**
     * 企业固定电话
     */
    private String telephone;
}
