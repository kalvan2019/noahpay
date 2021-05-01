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
 * 商户计费配置实体
 * 表名 fee_merchant
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "fee_merchant")
public class FeeMerchant implements Serializable {
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
     * 交易类型
     */
    @ShardingUk
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 计费方式
     */
    @Column(name = "fee_mode")
    private Integer feeMode;

    /**
     * 计费对象
     */
    @Column(name = "fee_object")
    private Integer feeObject;

    /**
     * 计费规则编号
     */
    @Column(name = "fee_rule")
    private String feeRule;

    /**
     * 绑定状态
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
