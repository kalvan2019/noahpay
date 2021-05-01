package com.noahpay.pay.commons.db.channel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.admin.annotation.ExtSuffix;
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
 * 渠道分段计费配置实体
 * 表名 channel_segment_fee
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "channel_segment_fee")
public class ChannelSegmentFee implements Serializable {
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
     * 分段计费规则
     */
    @ExcelProperty(value = "分段计费规则")
    @Column(name = "fee_segment_rule")
    private String feeSegmentRule;

    /**
     * 开始金额
     */
    @ExcelProperty(value = "开始金额",converter = LongStringConverter.class)
    @Column(name = "begin_amount")
    private Long beginAmount;

    /**
     * 结束金额
     */
    @ExcelProperty(value = "结束金额",converter = LongStringConverter.class)
    @Column(name = "end_amount")
    private Long endAmount;

    /**
     * 计费方法
     */
    @ExcelProperty(value = "计费方法",converter = IntegerStringConverter.class)
    @Dict(DictType.FEE_TYPE)
    @Column(name = "fee_type")
    private Integer feeType;

    /**
     * 费率
     */
    @ExcelProperty(value = "费率",converter = LongStringConverter.class)
    @ExtSuffix("/万")
    @Column(name = "fee_rate")
    private Long feeRate;

    /**
     * 固定收费
     */
    @ExcelProperty(value = "固定收费",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "fix_fee")
    private Long fixFee;

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
