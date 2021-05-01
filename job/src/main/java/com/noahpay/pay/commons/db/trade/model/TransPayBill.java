package com.noahpay.pay.commons.db.trade.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
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
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 交易流水号
     */
    @Column(name = "trans_id")
    private String transId;

    /**
     * 订单日期
     */
    @Column(name = "order_date")
    private Integer orderDate;

    /**
     * 支付日期
     */
    @Column(name = "pay_date")
    private Integer payDate;

    /**
     * 银行帐号
     */
    @Column(name = "bank_account_no")
    private String bankAccountNo;

    /**
     * 银行户名
     */
    @Column(name = "bank_account_name")
    private String bankAccountName;

    /**
     * 银行帐户类型
     */
    @Column(name = "bank_account_type")
    private Integer bankAccountType;

    /**
     * 银行卡有效期
     */
    @Column(name = "bank_account_expiry")
    private String bankAccountExpiry;

    /**
     * 行别
     */
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 行名
     */
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 银行协议号
     */
    @Column(name = "bank_protocol")
    private String bankProtocol;

    /**
     * 客户手机
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 证件类型
     */
    @Column(name = "certificate_type")
    private Integer certificateType;

    /**
     * 证件号码
     */
    @Column(name = "certificate_no")
    private String certificateNo;

    /**
     * 支付类型
     */
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 支付网关返回码
     */
    @Column(name = "pay_result_code")
    private String payResultCode;

    /**
     * 支付网关备注
     */
    @Column(name = "pay_result_note")
    private String payResultNote;

    /**
     * 渠道编号
     */
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 送往渠道流水号
     */
    @Column(name = "channel_send_sn")
    private String channelSendSn;

    /**
     * 送往渠道时间
     */
    @Column(name = "channel_send_time")
    private Date channelSendTime;

    /**
     * 送往渠道扩展数据
     */
    @Column(name = "channel_send_ext")
    private String channelSendExt;

    /**
     * 渠道商户号
     */
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 渠道商户名
     */
    @Column(name = "channel_merchant_name")
    private String channelMerchantName;

    /**
     * 渠道子商户号
     */
    @Column(name = "channel_sub_merchant_no")
    private String channelSubMerchantNo;

    /**
     * 送往渠道金额
     */
    @Column(name = "channel_amount")
    private Long channelAmount;

    /**
     * 渠道类目
     */
    @Column(name = "channel_mcc")
    private String channelMcc;

    /**
     * 渠道城市
     */
    @Column(name = "channel_city")
    private String channelCity;

    /**
     * 渠道账户类型
     */
    @Column(name = "channel_bank_account_type")
    private String channelBankAccountType;

    /**
     * 渠道行别
     */
    @Column(name = "channel_bank_type")
    private String channelBankType;

    /**
     * 渠道证件类型
     */
    @Column(name = "channel_certificate_type")
    private String channelCertificateType;

    /**
     * 渠道会计日期
     */
    @Column(name = "channel_account_date")
    private Integer channelAccountDate;

    /**
     * 渠道返回流水号
     */
    @Column(name = "channel_recv_sn")
    private String channelRecvSn;

    /**
     * 渠道返回时间
     */
    @Column(name = "channel_recv_time")
    private Date channelRecvTime;

    /**
     * 渠道返回扩展数据
     */
    @Column(name = "channel_recv_ext")
    private String channelRecvExt;

    /**
     * 渠道返回链接
     */
    @Column(name = "channel_recv_url")
    private String channelRecvUrl;

    /**
     * 渠道返回码
     */
    @Column(name = "channel_result_code")
    private String channelResultCode;

    /**
     * 渠道返回备注
     */
    @Column(name = "channel_result_note")
    private String channelResultNote;

    /**
     * 交易状态
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 对账状态
     */
    @Column(name = "check_state")
    private Integer checkState;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

}
