package com.noahpay.pay.commons.db.channel.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 通道商户信息实体
 * 表名 channel_merchant_pool
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "channel_merchant_pool")
public class ChannelMerchantPool implements Serializable {
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
     * 通道商户号
     */
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 通道子级商户号
     */
    @Column(name = "channel_sub_merchant_no")
    private String channelSubMerchantNo;

    /**
     * 子商户名称
     */
    @Column(name = "channel_sub_merchant_name")
    private String channelSubMerchantName;

    /**
     * 商户号
     */
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 城市代码
     */
    @Column(name = "city")
    private Integer city;

    /**
     * 行业类别
     */
    @Column(name = "mcc")
    private Integer mcc;

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
     * 日使用金额
     */
    @Column(name = "day_use_amount")
    private Long dayUseAmount;

    /**
     * 日限额
     */
    @Column(name = "day_limit_amount")
    private Long dayLimitAmount;

    /**
     * 月使用金额
     */
    @Column(name = "month_use_amount")
    private Long monthUseAmount;

    /**
     * 月金额限额
     */
    @Column(name = "month_limit_amount")
    private Long monthLimitAmount;

    /**
     * 商户状态
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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
