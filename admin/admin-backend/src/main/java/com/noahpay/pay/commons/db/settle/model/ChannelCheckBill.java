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
 * 渠道对账明细实体
 * 表名 channel_check_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "channel_check_bill")
public class ChannelCheckBill implements Serializable {
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
     * 渠道编号
     */
    @ExcelProperty(value = "渠道编号",converter = IntegerStringConverter.class)
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 渠道商户号
     */
    @ExcelProperty(value = "渠道商户号")
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 送往渠道流水号
     */
    @ExcelProperty(value = "送往渠道流水号")
    @Column(name = "channel_send_sn")
    private String channelSendSn;

    /**
     * 渠道返回流水号
     */
    @ExcelProperty(value = "渠道返回流水号")
    @Column(name = "channel_recv_sn")
    private String channelRecvSn;

    /**
     * 对账类型
     */
    @ExcelProperty(value = "对账类型",converter = IntegerStringConverter.class)
    @Column(name = "check_type")
    private Integer checkType;

    /**
     * 交易状态
     */
    @ExcelProperty(value = "交易状态",converter = IntegerStringConverter.class)
    @Column(name = "trans_state")
    private Integer transState;

    /**
     * 对账状态
     */
    @ExcelProperty(value = "对账状态",converter = IntegerStringConverter.class)
    @Column(name = "check_state")
    private Integer checkState;

    /**
     * 对账日期
     */
    @ExcelProperty(value = "对账日期",converter = IntegerStringConverter.class)
    @Column(name = "check_date")
    private Integer checkDate;

    /**
     * 交易时间
     */
    @ExcelProperty(value = "交易时间")
    @Column(name = "trans_time")
    private String transTime;

    /**
     * 订单金额
     */
    @ExcelProperty(value = "订单金额",converter = LongStringConverter.class)
    @Column(name = "order_amount")
    private Long orderAmount;

    /**
     * 渠道手续费
     */
    @ExcelProperty(value = "渠道手续费",converter = LongStringConverter.class)
    @Column(name = "channel_fee")
    private Long channelFee;

    /**
     * 银行帐号
     */
    @ExcelProperty(value = "银行帐号")
    @Column(name = "bank_account_no")
    private String bankAccountNo;

    /**
     * 银行户名
     */
    @ExcelProperty(value = "银行户名")
    @Column(name = "bank_account_name")
    private String bankAccountName;

    /**
     * 原送往渠道流水号
     */
    @ExcelProperty(value = "原送往渠道流水号")
    @Column(name = "org_channel_send_sn")
    private String orgChannelSendSn;

    /**
     * 原订单金额
     */
    @ExcelProperty(value = "原订单金额",converter = LongStringConverter.class)
    @Column(name = "org_order_amount")
    private Long orgOrderAmount;

    /**
     * 原渠道手续费
     */
    @ExcelProperty(value = "原渠道手续费",converter = LongStringConverter.class)
    @Column(name = "org_channel_fee")
    private Long orgChannelFee;

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
