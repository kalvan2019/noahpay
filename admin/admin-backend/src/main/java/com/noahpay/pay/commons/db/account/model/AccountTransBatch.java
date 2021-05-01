package com.noahpay.pay.commons.db.account.model;

import com.kalvan.admin.annotation.Dict;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 账户记账事务控制表实体
 * 表名 account_trans_batch
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "account_trans_batch")
public class AccountTransBatch implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 记账批次号
     */
    @Column(name = "account_trans_id")
    private String accountTransId;

    /**
     * 交易类型
     */
    @Dict(DictType.TRANS_TYPE)
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 是否已冲正
     */
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "undo_flag")
    private Integer undoFlag;

    /**
     * 会计日期
     */
    @Column(name = "account_date")
    private Integer accountDate;

}
