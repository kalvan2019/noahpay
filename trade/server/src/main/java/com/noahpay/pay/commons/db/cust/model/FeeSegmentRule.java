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
 * 分段计费规则配置实体
 * 表名 fee_segment_rule
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "fee_segment_rule")
public class FeeSegmentRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 分段计费规则
     */
    @Column(name = "fee_segment_rule")
    private String feeSegmentRule;

    /**
     * 开始金额
     */
    @Column(name = "begin_amount")
    private Long beginAmount;

    /**
     * 结束金额
     */
    @Column(name = "end_amount")
    private Long endAmount;

    /**
     * 计费方法
     */
    @Column(name = "fee_type")
    private Integer feeType;

    /**
     * 费率
     */
    @Column(name = "fee_rate")
    private Long feeRate;

    /**
     * 固定收费
     */
    @Column(name = "fix_fee")
    private Long fixFee;

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
