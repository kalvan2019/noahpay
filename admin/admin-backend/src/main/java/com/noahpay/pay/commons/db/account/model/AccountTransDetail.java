package com.noahpay.pay.commons.db.account.model;

import com.kalvan.admin.annotation.Dict;
import com.kalvan.admin.annotation.NumberConvert;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 记账明细实体
 * 表名 account_trans_detail
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "account_trans_detail")
public class AccountTransDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 记账id
     */
    @Column(name = "account_trans_id")
    private String accountTransId;

    /**
     * 账户号
     */
    @Column(name = "account_no")
    private Long accountNo;

    /**
     * 客户号
     */
    @Column(name = "cust_no")
    private Long custNo;

    /**
     * 商户号
     */
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 会计日期
     */
    @Column(name = "account_date")
    private Integer accountDate;

    /**
     * 开始金额
     */
    @NumberConvert(value = 100)
    @Column(name = "begin_amount")
    private Long beginAmount;

    /**
     * 入账金额
     */
    @NumberConvert(value = 100)
    @Column(name = "income_amount")
    private Long incomeAmount;

    /**
     * 出账金额
     */
    @NumberConvert(value = 100)
    @Column(name = "outgo_amount")
    private Long outgoAmount;

    /**
     * 冻结金额
     */
    @NumberConvert(value = 100)
    @Column(name = "freeze_amount")
    private Long freezeAmount;

    /**
     * 解冻金额
     */
    @NumberConvert(value = 100)
    @Column(name = "unfreeze_amount")
    private Long unfreezeAmount;

    /**
     * 结束金额
     */
    @NumberConvert(value = 100)
    @Column(name = "end_amount")
    private Long endAmount;

    /**
     * 冲正标识
     */
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "undo_flag")
    private Integer undoFlag;

    /**
     * 订单号
     */
    @Column(name = "ref_order_id")
    private String refOrderId;

    /**
     * 交易流水号
     */
    @Column(name = "ref_trans_id")
    private String refTransId;

    /**
     * 交易类型
     */
    @Dict(DictType.TRANS_TYPE)
    @Column(name = "ref_trans_type")
    private Integer refTransType;

    /**
     * 凭证类型
     */
    @Dict(DictType.VOUCHER_TYPE)
    @Column(name = "voucher_type")
    private Integer voucherType;

    /**
     * 摘要
     */
    @Column(name = "zy")
    private String zy;

    /**
     * ip
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 备注
     */
    @Column(name = "note")
    private String note;

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
