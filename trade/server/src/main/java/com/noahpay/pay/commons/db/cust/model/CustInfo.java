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
 * 客户信息实体
 * 表名 cust_info
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "cust_info")
public class CustInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 客户号
     */
    @ShardingUk
    @Column(name = "cust_no")
    private Long custNo;

    /**
     * 证件号码
     */
    @Column(name = "certificate_no")
    private String certificateNo;

    /**
     * 客户名称
     */
    @Column(name = "certificate_name")
    private String certificateName;

    /**
     * 证件类型
     */
    @Column(name = "certificate_type")
    private Integer certificateType;

    /**
     * 证件地址
     */
    @Column(name = "certificate_address")
    private String certificateAddress;

    /**
     * 证件有效日期
     */
    @Column(name = "certificate_expiry")
    private Integer certificateExpiry;

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
     * 详细地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 客户状态
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 销户时间
     */
    @Column(name = "close_time")
    private Date closeTime;

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
