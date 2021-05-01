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
 * 合作方关系维护表实体
 * 表名 partner_relation_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "partner_relation_info")
public class PartnerRelationInfo implements Serializable {
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
     * 合作方编号
     */
    @ExcelProperty(value = "合作方编号",converter = LongStringConverter.class)
    @Column(name = "partner_no")
    private Long partnerNo;

    /**
     * 商户编号
     */
    @ExcelProperty(value = "商户编号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 交易成本费率
     */
    @ExcelProperty(value = "交易成本费率",converter = LongStringConverter.class)
    @ExtSuffix("/万")
    @Column(name = "fee_rate")
    private Long feeRate;

    /**
     * 提现成本手续费
     */
    @ExcelProperty(value = "提现成本手续费",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "withdraw_fee")
    private Long withdrawFee;

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
