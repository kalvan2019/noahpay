package com.noahpay.pay.commons.db.trade.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 异步事务控制实体
 * 表名 async_event_handle
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "async_event_handle")
public class AsyncEventHandle implements Serializable {
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
     * 交易流水号
     */
    @ExcelProperty(value = "交易流水号")
    @Column(name = "trans_id")
    private String transId;

    /**
     * 交易流水号
     */
    @ExcelProperty(value = "交易流水号",converter = LongStringConverter.class)
    @Column(name = "pay_id")
    private Long payId;

    /**
     * 交易日期
     */
    @ExcelProperty(value = "交易日期",converter = IntegerStringConverter.class)
    @Column(name = "trans_date")
    private Integer transDate;

    /**
     * 交易类型
     */
    @ExcelProperty(value = "交易类型",converter = IntegerStringConverter.class)
    @Dict(DictType.TRANS_TYPE)
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 账户号
     */
    @ExcelProperty(value = "账户号",converter = LongStringConverter.class)
    @Column(name = "account_no")
    private Long accountNo;

    /**
     * 事件类型
     */
    @ExcelProperty(value = "事件类型",converter = IntegerStringConverter.class)
    @Dict(DictType.EVENT_TYPE)
    @Column(name = "event_type")
    private Integer eventType;

    /**
     * 事件内容json
     */
    @ExcelProperty(value = "事件内容json")
    @Column(name = "event_content")
    private String eventContent;

    /**
     * 执行策略
     */
    @ExcelProperty(value = "执行策略",converter = IntegerStringConverter.class)
    @Dict(DictType.STRATEGY_TYPE)
    @Column(name = "strategy_type")
    private Integer strategyType;

    /**
     * 支付网关返回码
     */
    @ExcelProperty(value = "支付网关返回码")
    @Column(name = "result_code")
    private String resultCode;

    /**
     * 支付网关备注
     */
    @ExcelProperty(value = "支付网关备注")
    @Column(name = "result_note")
    private String resultNote;

    /**
     * 执行次数
     */
    @ExcelProperty(value = "执行次数",converter = IntegerStringConverter.class)
    @Column(name = "deal_times")
    private Integer dealTimes;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态",converter = IntegerStringConverter.class)
    @Dict(DictType.EVENT_STATE)
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
