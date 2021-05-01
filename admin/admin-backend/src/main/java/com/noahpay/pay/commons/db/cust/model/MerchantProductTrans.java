package com.noahpay.pay.commons.db.cust.model;

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
 * 商户交易业务入网实体
 * 表名 merchant_product_trans
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "merchant_product_trans")
public class MerchantProductTrans implements Serializable {
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
     * 单笔金额上限
     */
    @ExcelProperty(value = "单笔金额上限",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "limit_max_amount")
    private Long limitMaxAmount;

    /**
     * 日限额
     */
    @ExcelProperty(value = "日限额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "day_limit_amount")
    private Long dayLimitAmount;

    /**
     * 日限笔数
     */
    @ExcelProperty(value = "日限笔数",converter = LongStringConverter.class)
    @Column(name = "day_limit_number")
    private Long dayLimitNumber;

    /**
     * 月金额限额
     */
    @ExcelProperty(value = "月金额限额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "month_limit_amount")
    private Long monthLimitAmount;

    /**
     * 月笔数限额
     */
    @ExcelProperty(value = "月笔数限额",converter = LongStringConverter.class)
    @Column(name = "month_limit_number")
    private Long monthLimitNumber;

    /**
     * 生效日期
     */
    @ExcelProperty(value = "生效日期",converter = IntegerStringConverter.class)
    @Column(name = "effective_date")
    private Integer effectiveDate;

    /**
     * 失效日期
     */
    @ExcelProperty(value = "失效日期",converter = IntegerStringConverter.class)
    @Column(name = "expiry_date")
    private Integer expiryDate;

    /**
     * 客户协议审核
     */
    @ExcelProperty(value = "客户协议审核",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "sign_audit_type")
    private Integer signAuditType;

    /**
     * 签约验证手机
     */
    @ExcelProperty(value = "签约验证手机",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "sign_check_sms")
    private Integer signCheckSms;

    /**
     * 签约成功发短信
     */
    @ExcelProperty(value = "签约成功发短信",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "sign_send_sms")
    private Integer signSendSms;

    /**
     * 处理方式
     */
    @ExcelProperty(value = "处理方式",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "trans_deal_type")
    private Integer transDealType;

    /**
     * 交易审核
     */
    @ExcelProperty(value = "交易审核",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "trans_audit_type")
    private Integer transAuditType;

    /**
     * 交易检查客户协议
     */
    @ExcelProperty(value = "交易检查客户协议",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "trans_check_protocol")
    private Integer transCheckProtocol;

    /**
     * 交易验证手机
     */
    @ExcelProperty(value = "交易验证手机",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "trans_check_sms")
    private Integer transCheckSms;

    /**
     * 交易成功发短信
     */
    @ExcelProperty(value = "交易成功发短信",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "trans_send_sms")
    private Integer transSendSms;

    /**
     * 入网状态
     */
    @ExcelProperty(value = "入网状态",converter = IntegerStringConverter.class)
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
