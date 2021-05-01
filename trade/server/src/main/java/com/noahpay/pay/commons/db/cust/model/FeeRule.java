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
 * 计费规则配置实体
 * 表名 fee_rule
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "fee_rule")
public class FeeRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 计费规则编号
     */
    @Column(name = "fee_rule")
    private String feeRule;

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
     * 最低手续费
     */
    @Column(name = "min_fee")
    private Long minFee;

    /**
     * 分段计费规则
     */
    @Column(name = "fee_segment_rule")
    private String feeSegmentRule;

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
