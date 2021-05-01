package com.noahpay.pay.commons.db.cust.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 商户信息实体
 * 表名 merchant_info
 *
 * @author kalvan.tools:chenliang
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
     * 营业执照注册号
     */
    @Column(name = "business_license_no")
    private String businessLicenseNo;

    /**
     * 结算银行帐号
     */
    @Column(name = "bank_account_no")
    private String bankAccountNo;

    /**
     * 银行户名
     */
    @Column(name = "bank_account_name")
    private String bankAccountName;

    /**
     * 银行帐户类型
     */
    @Column(name = "bank_account_type")
    private Integer bankAccountType;

    /**
     * 行别
     */
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 行名
     */
    @Column(name = "bank_name")
    private String bankName;

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
