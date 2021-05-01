package com.noahpay.pay.commons.db.cust.model;

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
 * 商户计费配置实体
 * 表名 fee_merchant
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "fee_merchant")
public class FeeMerchant implements Serializable {
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
     * 商户号
     */
    @ExcelProperty(value = "商户号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 交易类型
     */
    @ExcelProperty(value = "交易类型",converter = IntegerStringConverter.class)
    @Dict(DictType.TRANS_TYPE)
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 计费方式
     */
    @ExcelProperty(value = "计费方式",converter = IntegerStringConverter.class)
    @Dict(DictType.FEE_MODE)
    @Column(name = "fee_mode")
    private Integer feeMode;

    /**
     * 计费对象
     */
    @ExcelProperty(value = "计费对象",converter = IntegerStringConverter.class)
    @Dict(DictType.FEE_OBJECT)
    @Column(name = "fee_object")
    private Integer feeObject;

    /**
     * 计费规则
     */
    @ExcelProperty(value = "计费规则")
    @Column(name = "fee_rule")
    private String feeRule;

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
