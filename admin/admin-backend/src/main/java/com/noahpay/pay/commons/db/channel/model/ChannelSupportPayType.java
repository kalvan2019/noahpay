package com.noahpay.pay.commons.db.channel.model;

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
 * 渠道支持支付方式实体
 * 表名 channel_support_pay_type
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "channel_support_pay_type")
public class ChannelSupportPayType implements Serializable {
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
    @Dict(DictType.CHANNEL_NO)
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 支付类型
     */
    @ExcelProperty(value = "支付类型",converter = IntegerStringConverter.class)
    @Dict(DictType.PAY_TYPE)
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 日使用金额
     */
    @ExcelProperty(value = "日使用金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "day_use_amount")
    private Long dayUseAmount;

    /**
     * 日限额
     */
    @ExcelProperty(value = "日限额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "day_limit_amount")
    private Long dayLimitAmount;

    /**
     * 日使用笔数
     */
    @ExcelProperty(value = "日使用笔数",converter = LongStringConverter.class)
    @Column(name = "day_use_number")
    private Long dayUseNumber;

    /**
     * 日成功交易笔数限制
     */
    @ExcelProperty(value = "日成功交易笔数限制",converter = LongStringConverter.class)
    @Column(name = "day_limit_number")
    private Long dayLimitNumber;

    /**
     * 月使用金额
     */
    @ExcelProperty(value = "月使用金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "month_use_amount")
    private Long monthUseAmount;

    /**
     * 月金额限额
     */
    @ExcelProperty(value = "月金额限额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "month_limit_amount")
    private Long monthLimitAmount;

    /**
     * 最后统计日期
     */
    @ExcelProperty(value = "最后统计日期",converter = IntegerStringConverter.class)
    @Column(name = "last_date")
    private Integer lastDate;

    /**
     * 支持非工作日
     */
    @ExcelProperty(value = "支持非工作日",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "holiday_support")
    private Integer holidaySupport;

    /**
     * 银行路由
     */
    @ExcelProperty(value = "银行路由",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "bank_route_enabled")
    private Integer bankRouteEnabled;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态",converter = IntegerStringConverter.class)
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
