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
 * 风险事件记录实体
 * 表名 risk_list
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "risk_list")
public class RiskList implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 关联id
     */
    @Column(name = "ref_trans_id")
    private String refTransId;

    /**
     * 风控等级
     */
    @Column(name = "risk_level")
    private Integer riskLevel;

    /**
     * 被风控参数
     */
    @Column(name = "risk_params")
    private String riskParams;

    /**
     * 被风控原因
     */
    @Column(name = "risk_reason")
    private String riskReason;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}
