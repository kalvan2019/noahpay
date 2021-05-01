package com.noahpay.pay.commons.db.channel.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import com.noahpay.pay.route.bean.model.BankInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 路由规则配置实体
 * 表名 route_rule
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "route_rule")
public class RouteRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 路由规则
     */
    @Column(name = "route_rule")
    private String routeRule;

    /**
     * 通道编号
     */
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 行别
     */
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 银行帐户类型
     */
    @Column(name = "bank_account_type")
    private Integer bankAccountType;

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
     * 开始日期
     */
    @Column(name = "begin_date")
    private Integer beginDate;

    /**
     * 结束日期
     */
    @Column(name = "end_date")
    private Integer endDate;

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
     * 优先级
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * 权重因子
     */
    @Column(name = "weight")
    private Integer weight;

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
     * 支持银行列表
     * 收银台路由返回
     */
    private List<BankInfo> bankInfos;

}
