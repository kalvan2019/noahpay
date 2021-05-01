package com.noahpay.pay.commons.db.cust.model;

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
 * 计费规则配置实体
 * 表名 fee_rule
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "fee_rule")
public class FeeRule implements Serializable {
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
     * 计费规则
     */
    @ExcelProperty(value = "计费规则")
    @Column(name = "fee_rule")
    private String feeRule;

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
     * 行别
     */
    @ExcelProperty(value = "行别")
    @Dict(DictType.BANK_TYPE)
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 银行帐户类型
     */
    @ExcelProperty(value = "银行帐户类型",converter = IntegerStringConverter.class)
    @Dict(DictType.BANK_ACCOUNT_TYPE)
    @Column(name = "bank_account_type")
    private Integer bankAccountType;

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
     * 最低手续费
     */
    @ExcelProperty(value = "最低手续费",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "min_fee")
    private Long minFee;

    /**
     * 分段计费规则
     */
    @ExcelProperty(value = "分段计费规则")
    @Column(name = "fee_segment_rule")
    private String feeSegmentRule;

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
