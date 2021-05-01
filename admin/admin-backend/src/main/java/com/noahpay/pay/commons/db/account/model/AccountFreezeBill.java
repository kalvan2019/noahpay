package com.noahpay.pay.commons.db.account.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.admin.annotation.NumberConvert;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 账户冻结解冻明细实体
 * 表名 account_freeze_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "account_freeze_bill")
public class AccountFreezeBill implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @ExcelProperty(value = "自增id",converter = LongStringConverter.class)
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 冻结id
     */
    @ExcelProperty(value = "冻结id")
    @Column(name = "account_freeze_id")
    private String accountFreezeId;

    /**
     * 关联交易流水
     */
    @ExcelProperty(value = "关联交易流水")
    @Column(name = "ref_trans_id")
    private String refTransId;

    /**
     * 账户号
     */
    @ExcelProperty(value = "账户号",converter = LongStringConverter.class)
    @Column(name = "account_no")
    private Long accountNo;

    /**
     * 客户号
     */
    @ExcelProperty(value = "客户号",converter = LongStringConverter.class)
    @Column(name = "cust_no")
    private Long custNo;

    /**
     * 冻结金额
     */
    @ExcelProperty(value = "冻结金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "freeze_amount")
    private Long freezeAmount;

    /**
     * 冻结原因
     */
    @ExcelProperty(value = "冻结原因")
    @Column(name = "freeze_reason")
    private String freezeReason;

    /**
     * 冻结日期
     */
    @ExcelProperty(value = "冻结日期",converter = IntegerStringConverter.class)
    @Column(name = "freeze_account_date")
    private Integer freezeAccountDate;

    /**
     * 解冻金额
     */
    @ExcelProperty(value = "解冻金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "unfreeze_amount")
    private Long unfreezeAmount;

    /**
     * 解冻原因
     */
    @ExcelProperty(value = "解冻原因")
    @Column(name = "unfreeze_reason")
    private String unfreezeReason;

    /**
     * 解冻日期
     */
    @ExcelProperty(value = "解冻日期",converter = IntegerStringConverter.class)
    @Column(name = "unfreeze_account_date")
    private Integer unfreezeAccountDate;

    /**
     * 冻结状态
     */
    @ExcelProperty(value = "冻结状态",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "state")
    private Integer state;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

}
