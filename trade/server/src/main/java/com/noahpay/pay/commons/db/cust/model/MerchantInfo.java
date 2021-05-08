package com.noahpay.pay.commons.db.cust.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 商户信息实体
 * 表名 merchant_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "merchant_info")
public class MerchantInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Long id;

    /**
     * 商户号
     */
    @ShardingUk
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 商户名
     */
    @Column(name = "merchant_name")
    private String merchantName;

    /**
     * 商户简称
     */
    @Column(name = "merchant_shortname")
    private String merchantShortname;

    /**
     * 商户类型
     */
    @Column(name = "merchant_type")
    private String merchantType;

    /**
     * 国家或区域
     */
    @Column(name = "merchant_country_code")
    private String merchantCountryCode;

    /**
     * 业务类目
     */
    @Column(name = "business_category")
    private String businessCategory;

    /**
     * 商户行业编码
     */
    @Column(name = "mcc")
    private String mcc;

    /**
     * 联系人姓名
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     * 联系人电话
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    @Column(name = "contact_email")
    private String contactEmail;

    /**
     * 结算银行帐号
     */
    @Column(name = "bank_account_no")
    private String bankAccountNo;

    /**
     * 结算银行户名
     */
    @Column(name = "bank_account_name")
    private String bankAccountName;

    /**
     * 行别
     */
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 联行号
     */
    @Column(name = "bank_code")
    private String bankCode;

    /**
     * 登录密码
     */
    @Column(name = "login_password")
    private String loginPassword;

    /**
     * 支付密码
     */
    @Column(name = "pay_password")
    private String payPassword;

    /**
     * 密码盐
     */
    @Column(name = "salt")
    private String salt;

    /**
     * 商户状态
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

}
