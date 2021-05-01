package com.noahpay.pay.commons.db.risk.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 黑名单实体
 * 表名 black_list
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "black_list")
public class BlackList implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 黒名单类型
     */
    @Column(name = "black_type")
    private Integer blackType;

    /**
     * 客户号
     */
    @Column(name = "cust_no")
    private Long custNo;

    /**
     * 客户名称
     */
    @Column(name = "cust_name")
    private String custName;

    /**
     * 证件号码
     */
    @Column(name = "certificate_no")
    private String certificateNo;

    /**
     * 银行帐号
     */
    @Column(name = "bank_account_no")
    private String bankAccountNo;

    /**
     * 客户邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 客户手机
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 营业执照注册号
     */
    @Column(name = "business_license_no")
    private String businessLicenseNo;

    /**
     * 风控状态
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
