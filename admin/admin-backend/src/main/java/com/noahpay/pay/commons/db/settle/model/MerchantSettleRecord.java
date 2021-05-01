package com.noahpay.pay.commons.db.settle.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 商户提现记录表实体
 * 表名 merchant_settle_record
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "merchant_settle_record")
public class MerchantSettleRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键",converter = LongStringConverter.class)
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 订单批次号
     */
    @ExcelProperty(value = "订单批次号")
    @Column(name = "batch_id")
    private String batchId;

    /**
     * 提现订单号
     */
    @ExcelProperty(value = "提现订单号")
    @Column(name = "order_id")
    private String orderId;

    /**
     * 交易流水号
     */
    @ExcelProperty(value = "交易流水号")
    @Column(name = "trans_id")
    private String transId;

    /**
     * 商户号
     */
    @ExcelProperty(value = "商户号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 银行账户人
     */
    @ExcelProperty(value = "银行账户人")
    @Column(name = "bank_account_name")
    private String bankAccountName;

    /**
     * 银行帐号
     */
    @ExcelProperty(value = "银行帐号")
    @Column(name = "bank_account_no")
    private String bankAccountNo;

    /**
     * 证件号
     */
    @ExcelProperty(value = "证件号")
    @Column(name = "certificate_no")
    private String certificateNo;

    /**
     * 银行帐户类型
     */
    @ExcelProperty(value = "银行帐户类型",converter = IntegerStringConverter.class)
    @Column(name = "bank_account_type")
    private Integer bankAccountType;

    /**
     * 行名
     */
    @ExcelProperty(value = "行名")
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 银行类别
     */
    @ExcelProperty(value = "银行类别")
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    @Column(name = "mobile")
    private String mobile;

    /**
     * 提现金额
     */
    @ExcelProperty(value = "提现金额",converter = LongStringConverter.class)
    @Column(name = "settle_amount")
    private Long settleAmount;

    /**
     * 提现手续费
     */
    @ExcelProperty(value = "提现手续费",converter = LongStringConverter.class)
    @Column(name = "settle_merchant_fee")
    private Long settleMerchantFee;

    /**
     * 提现日期
     */
    @ExcelProperty(value = "提现日期",converter = LongStringConverter.class)
    @Column(name = "settle_date")
    private Long settleDate;

    /**
     * 提现审核状态：0：通过；1.待审；2.不通过
     */
    @ExcelProperty(value = "提现审核状态：0：通过；1.待审；2.不通过",converter = IntegerStringConverter.class)
    @Column(name = "state")
    private Integer state;

    /**
     * 交易状态
     */
    @ExcelProperty(value = "交易状态",converter = IntegerStringConverter.class)
    @Column(name = "trans_state")
    private Integer transState;

    /**
     * 交易返回备注
     */
    @ExcelProperty(value = "交易返回备注")
    @Column(name = "result_note")
    private String resultNote;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @ExcelProperty(value = "修改时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

}
