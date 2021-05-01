package com.noahpay.pay.commons.db.settle.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 账户汇总日报表实体
 * 表名 account_report_day
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "account_report_day")
public class AccountReportDay implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 会计日期
     */
    @Column(name = "account_date")
    private Integer accountDate;

    /**
     * 交易类型
     */
    @Column(name = "ref_trans_type")
    private Integer refTransType;

    /**
     * 凭证类型
     */
    @Column(name = "voucher_type")
    private Integer voucherType;

    /**
     * 日借记发生额
     */
    @Column(name = "debit_amount")
    private Long debitAmount;

    /**
     * 日借记调入额
     */
    @Column(name = "debit_adjust_amount")
    private Long debitAdjustAmount;

    /**
     * 日贷记发生额
     */
    @Column(name = "credit_amount")
    private Long creditAmount;

    /**
     * 日贷记调出额
     */
    @Column(name = "credit_adjust_amount")
    private Long creditAdjustAmount;

    /**
     * 日冻结金额
     */
    @Column(name = "freeze_amount")
    private Long freezeAmount;

    /**
     * 日解冻金额
     */
    @Column(name = "unfreeze_amount")
    private Long unfreezeAmount;

    /**
     * 手续费收入
     */
    @Column(name = "income_fee")
    private Long incomeFee;

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
