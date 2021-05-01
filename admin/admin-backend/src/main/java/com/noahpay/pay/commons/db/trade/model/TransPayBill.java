package com.noahpay.pay.commons.db.trade.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.admin.annotation.NumberConvert;
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
 * 交易订单支付明细实体
 * 表名 trans_pay_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "trans_pay_bill")
public class TransPayBill implements Serializable {
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
     * 订单日期
     */
    @ExcelProperty(value = "订单日期",converter = IntegerStringConverter.class)
    @Column(name = "order_date")
    private Integer orderDate;

    /**
     * 支付日期
     */
    @ExcelProperty(value = "支付日期",converter = IntegerStringConverter.class)
    @Column(name = "pay_date")
    private Integer payDate;

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
    @Desensitized(type = SensitiveType.CHINESE_NAME)
    @ExcelProperty(value = "银行户名")
    @Column(name = "bank_account_name")
    private String bankAccountName;

    /**
     * 银行帐户类型
     */
    @ExcelProperty(value = "银行帐户类型",converter = IntegerStringConverter.class)
    @Dict(DictType.BANK_ACCOUNT_TYPE)
    @Column(name = "bank_account_type")
    private Integer bankAccountType;

    /**
     * 银行卡有效期
     */
    @ExcelProperty(value = "银行卡有效期")
    @Column(name = "bank_account_expiry")
    private String bankAccountExpiry;

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
     * 银行协议号
     */
    @ExcelProperty(value = "银行协议号")
    @Column(name = "bank_protocol")
    private String bankProtocol;

    /**
     * 客户手机
     */
    @Desensitized(type = SensitiveType.MOBILE_PHONE)
    @ExcelProperty(value = "客户手机")
    @Column(name = "mobile")
    private String mobile;

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
    @Desensitized(type = SensitiveType.ID_CARD)
    @ExcelProperty(value = "证件号码")
    @Column(name = "certificate_no")
    private String certificateNo;

    /**
     * 支付类型
     */
    @ExcelProperty(value = "支付类型",converter = IntegerStringConverter.class)
    @Dict(DictType.PAY_TYPE)
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 返回码
     */
    @ExcelProperty(value = "返回码")
    @Column(name = "pay_result_code")
    private String payResultCode;

    /**
     * 返回描述
     */
    @ExcelProperty(value = "返回描述")
    @Column(name = "pay_result_note")
    private String payResultNote;

    /**
     * 渠道编号
     */
    @ExcelProperty(value = "渠道编号",converter = IntegerStringConverter.class)
    @Dict(DictType.CHANNEL_NO)
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 送往渠道流水号
     */
    @ExcelProperty(value = "送往渠道流水号")
    @Column(name = "channel_send_sn")
    private String channelSendSn;

    /**
     * 送往渠道时间
     */
    @ExcelProperty(value = "送往渠道时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "channel_send_time")
    private Date channelSendTime;

    /**
     * 送往渠道扩展数据
     */
    @ExcelProperty(value = "送往渠道扩展数据")
    @Column(name = "channel_send_ext")
    private String channelSendExt;

    /**
     * 渠道商户号
     */
    @ExcelProperty(value = "渠道商户号")
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 渠道商户名
     */
    @ExcelProperty(value = "渠道商户名")
    @Column(name = "channel_merchant_name")
    private String channelMerchantName;

    /**
     * 渠道子商户号
     */
    @ExcelProperty(value = "渠道子商户号")
    @Column(name = "channel_sub_merchant_no")
    private String channelSubMerchantNo;

    /**
     * 送往渠道金额
     */
    @ExcelProperty(value = "送往渠道金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "channel_amount")
    private Long channelAmount;

    /**
     * 渠道类目
     */
    @ExcelProperty(value = "渠道类目")
    @Column(name = "channel_mcc")
    private String channelMcc;

    /**
     * 渠道城市
     */
    @ExcelProperty(value = "渠道城市")
    @Column(name = "channel_city")
    private String channelCity;

    /**
     * 渠道账户类型
     */
    @ExcelProperty(value = "渠道账户类型")
    @Column(name = "channel_bank_account_type")
    private String channelBankAccountType;

    /**
     * 渠道行别
     */
    @ExcelProperty(value = "渠道行别")
    @Column(name = "channel_bank_type")
    private String channelBankType;

    /**
     * 渠道证件类型
     */
    @ExcelProperty(value = "渠道证件类型")
    @Column(name = "channel_certificate_type")
    private String channelCertificateType;

    /**
     * 渠道会计日期
     */
    @ExcelProperty(value = "渠道会计日期",converter = IntegerStringConverter.class)
    @Column(name = "channel_account_date")
    private Integer channelAccountDate;

    /**
     * 渠道返回流水号
     */
    @ExcelProperty(value = "渠道返回流水号")
    @Column(name = "channel_recv_sn")
    private String channelRecvSn;

    /**
     * 渠道返回时间
     */
    @ExcelProperty(value = "渠道返回时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "channel_recv_time")
    private Date channelRecvTime;

    /**
     * 渠道返回扩展数据
     */
    @ExcelProperty(value = "渠道返回扩展数据")
    @Column(name = "channel_recv_ext")
    private String channelRecvExt;

    /**
     * 渠道返回链接
     */
    @ExcelProperty(value = "渠道返回链接")
    @Column(name = "channel_recv_url")
    private String channelRecvUrl;

    /**
     * 渠道返回码
     */
    @ExcelProperty(value = "渠道返回码")
    @Column(name = "channel_result_code")
    private String channelResultCode;

    /**
     * 渠道返回描述
     */
    @ExcelProperty(value = "渠道返回描述")
    @Column(name = "channel_result_note")
    private String channelResultNote;

    /**
     * 交易状态
     */
    @ExcelProperty(value = "交易状态",converter = IntegerStringConverter.class)
    @Dict(DictType.TRANS_STATE)
    @Column(name = "state")
    private Integer state;

    /**
     * 对账状态
     */
    @ExcelProperty(value = "对账状态",converter = IntegerStringConverter.class)
    @Dict(DictType.CHECK_STATE)
    @Column(name = "check_state")
    private Integer checkState;

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
