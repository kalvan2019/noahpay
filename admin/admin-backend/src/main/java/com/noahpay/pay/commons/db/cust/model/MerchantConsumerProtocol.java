package com.noahpay.pay.commons.db.cust.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import com.kalvan.sensitive.annotation.Desensitized;
import com.kalvan.sensitive.enums.SensitiveType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 商户用户银行协议实体
 * 表名 merchant_consumer_protocol
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "merchant_consumer_protocol")
public class MerchantConsumerProtocol implements Serializable {
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
     * 下游协议号
     */
    @ExcelProperty(value = "下游协议号")
    @Column(name = "merchant_protocol_no")
    private String merchantProtocolNo;

    /**
     * 子商户号
     */
    @ExcelProperty(value = "子商户号",converter = LongStringConverter.class)
    @Column(name = "sub_merchant_no")
    private Long subMerchantNo;

    /**
     * 渠道编号
     */
    @ExcelProperty(value = "渠道编号",converter = IntegerStringConverter.class)
    @Dict(DictType.CHANNEL_NO)
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 渠道商户号
     */
    @ExcelProperty(value = "渠道商户号")
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 渠道子商户号
     */
    @ExcelProperty(value = "渠道子商户号")
    @Column(name = "channel_sub_merchant_no")
    private String channelSubMerchantNo;

    /**
     * 生效时间
     */
    @ExcelProperty(value = "生效时间",converter = IntegerStringConverter.class)
    @Column(name = "effective_time")
    private Integer effectiveTime;

    /**
     * 失效时间
     */
    @ExcelProperty(value = "失效时间",converter = IntegerStringConverter.class)
    @Column(name = "expiry_time")
    private Integer expiryTime;

    /**
     * 单笔金额上限
     */
    @ExcelProperty(value = "单笔金额上限",converter = LongStringConverter.class)
    @Column(name = "limit_max_amount")
    private Long limitMaxAmount;

    /**
     * 银行协议号
     */
    @ExcelProperty(value = "银行协议号")
    @Column(name = "bank_protocol_no")
    private String bankProtocolNo;

    /**
     * 银行帐号
     */
    @Desensitized(type = SensitiveType.BANK_CARD)
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
     * 银行帐户类型
     */
    @ExcelProperty(value = "银行帐户类型",converter = IntegerStringConverter.class)
    @Column(name = "bank_account_type")
    private Integer bankAccountType;

    /**
     * 银行失效日期
     */
    @ExcelProperty(value = "银行失效日期")
    @Column(name = "bank_account_expiry")
    private String bankAccountExpiry;

    /**
     * 账户级别
     */
    @ExcelProperty(value = "账户级别",converter = IntegerStringConverter.class)
    @Column(name = "bank_account_level")
    private Integer bankAccountLevel;

    /**
     * 行别
     */
    @ExcelProperty(value = "行别")
    @Dict(DictType.BANK_TYPE)
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 行名
     */
    @ExcelProperty(value = "行名")
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 客户手机
     */
    @Desensitized(type = SensitiveType.MOBILE_PHONE)
    @ExcelProperty(value = "客户手机")
    @Column(name = "mobile")
    private String mobile;

    /**
     * 扩展字段
     */
    @ExcelProperty(value = "扩展字段")
    @Column(name = "ext_data")
    private String extData;

    /**
     * 支付类型
     */
    @ExcelProperty(value = "支付类型",converter = IntegerStringConverter.class)
    @Dict(DictType.PAY_TYPE)
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 证件类型
     */
    @ExcelProperty(value = "证件类型",converter = IntegerStringConverter.class)
    @Dict(DictType.CERTIFICATE_TYPE)
    @Column(name = "certificate_type")
    private Integer certificateType;

    /**
     * 证件号码
     */
    @Desensitized(type = SensitiveType.MOBILE_PHONE)
    @ExcelProperty(value = "证件号码")
    @Column(name = "certificate_no")
    private String certificateNo;

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
