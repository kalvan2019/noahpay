package com.noahpay.pay.commons.db.channel.model;

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
 * 渠道列表实体
 * 表名 channel_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "channel_info")
public class ChannelInfo implements Serializable {
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
     * 渠道名称
     */
    @ExcelProperty(value = "渠道名称")
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 实现类名
     */
    @ExcelProperty(value = "实现类名")
    @Column(name = "channel_class")
    private String channelClass;

    /**
     * 渠道商户号
     */
    @ExcelProperty(value = "渠道商户号")
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 大商户轮询
     */
    @ExcelProperty(value = "大商户轮询",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "merchant_pool_convert")
    private Integer merchantPoolConvert;

    /**
     * 转换多商户
     */
    @ExcelProperty(value = "转换多商户",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "multi_merchant_convert")
    private Integer multiMerchantConvert;

    /**
     * 转换发往银行流水号
     */
    @ExcelProperty(value = "转换发往银行流水号",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "send_sn_convert")
    private Integer sendSnConvert;

    /**
     * 转换行别
     */
    @ExcelProperty(value = "转换行别",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "bank_type_convert")
    private Integer bankTypeConvert;

    /**
     * 转换银行账户类型
     */
    @ExcelProperty(value = "转换银行账户类型",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "bank_account_type_convert")
    private Integer bankAccountTypeConvert;

    /**
     * 转换证件类型
     */
    @ExcelProperty(value = "转换证件类型",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "certificate_type_convert")
    private Integer certificateTypeConvert;

    /**
     * 转换城市代码
     */
    @ExcelProperty(value = "转换城市代码",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "city_convert")
    private Integer cityConvert;

    /**
     * 行业类目转换
     */
    @ExcelProperty(value = "行业类目转换",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "mcc_convert")
    private Integer mccConvert;

    /**
     * 转换客户号类型
     */
    @ExcelProperty(value = "转换客户号类型",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "cust_convert")
    private Integer custConvert;

    /**
     * 连接超时时间
     */
    @ExcelProperty(value = "连接超时时间",converter = IntegerStringConverter.class)
    @Column(name = "connection_timeout")
    private Integer connectionTimeout;

    /**
     * 渠道最大并发数
     */
    @ExcelProperty(value = "渠道最大并发数",converter = IntegerStringConverter.class)
    @Column(name = "connection_max_num")
    private Integer connectionMaxNum;

    /**
     * 读超时时间
     */
    @ExcelProperty(value = "读超时时间",converter = IntegerStringConverter.class)
    @Column(name = "read_timeout")
    private Integer readTimeout;

    /**
     * 渠道发送短信
     */
    @ExcelProperty(value = "渠道发送短信",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "sms_support")
    private Integer smsSupport;

    /**
     * 支持对账 0-支持对账;1-不支持
     */
    @ExcelProperty(value = "支持对账 0-支持对账;1-不支持",converter = IntegerStringConverter.class)
    @Column(name = "check_support")
    private Integer checkSupport;

    /**
     * 对账时间
     */
    @ExcelProperty(value = "对账时间")
    @Column(name = "check_time")
    private String checkTime;

    /**
     * 对账凭证
     */
    @ExcelProperty(value = "对账凭证",converter = IntegerStringConverter.class)
    @Column(name = "check_field")
    private Integer checkField;

    /**
     * 结算周期
     */
    @ExcelProperty(value = "结算周期")
    @Column(name = "settlement_time")
    private String settlementTime;

    /**
     * 结算账号
     */
    @ExcelProperty(value = "结算账号")
    @Column(name = "settlement_bank_account_no")
    private String settlementBankAccountNo;

    /**
     * 结算户名
     */
    @ExcelProperty(value = "结算户名")
    @Column(name = "settlement_bank_account_name")
    private String settlementBankAccountName;

    /**
     * 结算银行
     */
    @ExcelProperty(value = "结算银行")
    @Dict(DictType.BANK_TYPE)
    @Column(name = "settlement_bank_type")
    private String settlementBankType;

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
