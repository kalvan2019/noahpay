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
 * 通道支持支付方式实体
 * 表名 channel_support_pay_type
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "channel_support_pay_type")
public class ChannelSupportPayType implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 通道编号
     */
    @ShardingUk
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 支付类型
     */
    @ShardingUk
    @Column(name = "pay_type")
    private String payType;

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
     * 日使用笔数
     */
    @Column(name = "day_use_number")
    private Long dayUseNumber;

    /**
     * 日成功交易笔数限制
     */
    @Column(name = "day_limit_number")
    private Long dayLimitNumber;

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
     * 最后统计日期
     */
    @Column(name = "last_date")
    private Integer lastDate;

    /**
     * 支持非工作日
     */
    @Column(name = "holiday_support")
    private Integer holidaySupport;

    /**
     * 银行路由
     */
    @Column(name = "bank_route_enabled")
    private Integer bankRouteEnabled;

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
}
