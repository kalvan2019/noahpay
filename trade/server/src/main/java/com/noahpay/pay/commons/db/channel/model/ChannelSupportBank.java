package com.noahpay.pay.commons.db.channel.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 通道支持银行实体
 * 表名 channel_support_bank
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "channel_support_bank")
public class ChannelSupportBank implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 通道编号
     */
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 支付类型
     */
    @Column(name = "pay_type")
    private String payType;

    /**
     * 单笔金额下限
     */
    @Column(name = "limit_min_amount")
    private Long limitMinAmount;

    /**
     * 单笔金额上限
     */
    @Column(name = "limit_max_amount")
    private Long limitMaxAmount;

    /**
     * 开始时间
     */
    @Column(name = "begin_time")
    private String beginTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private String endTime;

    /**
     * 银行帐户类型
     */
    @Column(name = "bank_account_type")
    private Integer bankAccountType;

    /**
     * 行别组
     */
    @Column(name = "bank_type_group")
    private String bankTypeGroup;

    /**
     * 地区组
     */
    @Column(name = "bank_city_group")
    private String bankCityGroup;

    /**
     * 银行组支持
     */
    @Column(name = "bank_support")
    private Integer bankSupport;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 状态
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

    /**
     * 行别
     */
    @Transient
    private String bankType;
}
