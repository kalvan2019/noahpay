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
 * 对账结果差异明细实体
 * 表名 channel_check_diff
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "channel_check_diff")
public class ChannelCheckDiff implements Serializable {
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
     * 订单号
     */
    @ExcelProperty(value = "订单号")
    @Column(name = "order_id")
    private String orderId;

    /**
     * 订单金额
     */
    @ExcelProperty(value = "订单金额",converter = LongStringConverter.class)
    @Column(name = "order_amount")
    private Long orderAmount;

    /**
     * 手续费
     */
    @ExcelProperty(value = "手续费",converter = LongStringConverter.class)
    @Column(name = "order_fee")
    private Long orderFee;

    /**
     * 交易流水号
     */
    @ExcelProperty(value = "交易流水号")
    @Column(name = "trans_id")
    private String transId;

    /**
     * 交易日期
     */
    @ExcelProperty(value = "交易日期",converter = IntegerStringConverter.class)
    @Column(name = "trans_date")
    private Integer transDate;

    /**
     * 对账日期
     */
    @ExcelProperty(value = "对账日期",converter = IntegerStringConverter.class)
    @Column(name = "check_date")
    private Integer checkDate;

    /**
     * 交易类型
     */
    @ExcelProperty(value = "交易类型",converter = IntegerStringConverter.class)
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 账户号
     */
    @ExcelProperty(value = "账户号",converter = LongStringConverter.class)
    @Column(name = "account_no")
    private Long accountNo;

    /**
     * 发往渠道流水号
     */
    @ExcelProperty(value = "发往渠道流水号")
    @Column(name = "channel_send_sn")
    private String channelSendSn;

    /**
     * 渠道返回流水号
     */
    @ExcelProperty(value = "渠道返回流水号")
    @Column(name = "channel_recv_sn")
    private String channelRecvSn;

    /**
     * 渠道交易金额
     */
    @ExcelProperty(value = "渠道交易金额",converter = LongStringConverter.class)
    @Column(name = "channel_send_amount")
    private Long channelSendAmount;

    /**
     * 渠道返回金额
     */
    @ExcelProperty(value = "渠道返回金额",converter = LongStringConverter.class)
    @Column(name = "channel_recv_amount")
    private Long channelRecvAmount;

    /**
     * 对账结果
     */
    @ExcelProperty(value = "对账结果",converter = IntegerStringConverter.class)
    @Column(name = "diff_state")
    private Integer diffState;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @Column(name = "remark")
    private String remark;

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

    /**
     * 渠道编号
     */
    @ExcelProperty(value = "渠道编号",converter = IntegerStringConverter.class)
    @Column(name = "channel_no")
    private Integer channelNo;

}
