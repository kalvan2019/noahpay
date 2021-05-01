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
 * 商户交易额度累计实体
 * 表名 merchant_trade_sum
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "merchant_trans_sum")
public class MerchantTransSum implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 交易日期
     */
    @Column(name = "trans_date")
    private Integer transDate;

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
     * 日使用笔数
     */
    @Column(name = "day_use_number")
    private Integer dayUseNumber;

    /**
     * 日使用金额
     */
    @Column(name = "day_use_amount")
    private Long dayUseAmount;

    /**
     * 月使用笔数
     */
    @Column(name = "month_use_number")
    private Integer monthUseNumber;

    /**
     * 月使用金额
     */
    @Column(name = "month_use_amount")
    private Long monthUseAmount;

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
