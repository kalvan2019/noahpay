package com.noahpay.pay.commons.db.trade.model;

import com.kalvan.admin.annotation.Dict;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 退款交易流水实体
 * 表名 refund_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "refund_bill")
public class RefundBill implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 交易流水
     */
    @Column(name = "trans_id")
    private String transId;

    /**
     * 交易日期
     */
    @Column(name = "trans_date")
    private Integer transDate;

    /**
     * 交易类型
     */
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 订单号
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 订单日期
     */
    @Column(name = "order_date")
    private Integer orderDate;

    /**
     * 退款订单说明
     */
    @Column(name = "order_note")
    private String orderNote;

    /**
     * 退款金额
     */
    @Column(name = "order_amount")
    private Long orderAmount;

    /**
     * 域名
     */
    @Column(name = "order_domain")
    private String orderDomain;

    /**
     * IP
     */
    @Column(name = "order_ip")
    private String orderIp;

    /**
     * 终端IP
     */
    @Column(name = "order_terminal_ip")
    private String orderTerminalIp;

    /**
     * 设备指纹
     */
    @Column(name = "order_terminal_device")
    private String orderTerminalDevice;

    /**
     * 后台通知地址
     */
    @Column(name = "notify_bg_url")
    private String notifyBgUrl;

    /**
     * 商户号
     */
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 商户客户号
     */
    @Column(name = "merchant_cust_no")
    private Long merchantCustNo;

    /**
     * 商户账户号
     */
    @Column(name = "merchant_account_no")
    private Long merchantAccountNo;

    /**
     * 退回商户手续费
     */
    @Column(name = "merchant_fee")
    private Long merchantFee;

    /**
     * 子商户号
     */
    @Column(name = "sub_merchant_no")
    private Long subMerchantNo;

    /**
     * 子商户客户号
     */
    @Column(name = "sub_merchant_cust_no")
    private Long subMerchantCustNo;

    /**
     * 子商户账户号
     */
    @Column(name = "sub_merchant_account_no")
    private Long subMerchantAccountNo;

    /**
     * 退回子商户手续费
     */
    @Column(name = "sub_merchant_fee")
    private Long subMerchantFee;

    /**
     * 消费者手续费
     */
    @Column(name = "consumer_fee")
    private Long consumerFee;

    /**
     * 记账流水号
     */
    @Column(name = "account_trans_id")
    private String accountTransId;

    /**
     * 账务记账时间
     */
    @Column(name = "account_trans_time")
    private Date accountTransTime;

    /**
     * 会计日期
     */
    @Column(name = "account_date")
    private Integer accountDate;

    /**
     * 冲正记账流水号
     */
    @Column(name = "undo_account_trans_id")
    private String undoAccountTransId;

    /**
     * 冲正记账时间
     */
    @Column(name = "undo_account_trans_time")
    private Date undoAccountTransTime;

    /**
     * 冲正会计日期
     */
    @Column(name = "undo_account_date")
    private Integer undoAccountDate;

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
     * 渠道返回码
     */
    @Column(name = "channel_return_code")
    private String channelReturnCode;

    /**
     * 渠道返回备注
     */
    @Column(name = "channel_result_note")
    private String channelResultNote;

    /**
     * 退款交易状态
     */
    @Dict(DictType.TRANS_STATE)
    @Column(name = "state")
    private Integer state;

    /**
     * 通知状态
     */
    @Column(name = "notify_state")
    private Integer notifyState;

    /**
     * 对账状态
     */
    @Column(name = "check_state")
    private Integer checkState;

    /**
     * 原交易流水
     */
    @Column(name = "org_trans_id")
    private String orgTransId;

    /**
     * 原交易日期
     */
    @Column(name = "org_trans_date")
    private Integer orgTransDate;

    /**
     * 原交易类型
     */
    @Column(name = "org_trans_type")
    private Integer orgTransType;

    /**
     * 原订单号
     */
    @Column(name = "org_order_id")
    private String orgOrderId;

    /**
     * 原订单日期
     */
    @Column(name = "org_order_date")
    private Integer orgOrderDate;

    /**
     * 原订单金额
     */
    @Column(name = "org_order_amount")
    private Long orgOrderAmount;

    /**
     * 原会计日期
     */
    @Column(name = "org_account_date")
    private Integer orgAccountDate;

    /**
     * 原支付类型
     */
    @Column(name = "org_pay_type")
    private Integer orgPayType;

    /**
     * 原送往渠道流水号
     */
    @Column(name = "org_channel_send_sn")
    private String orgChannelSendSn;

    /**
     * 原渠道返回流水号
     */
    @Column(name = "org_channel_recv_sn")
    private String orgChannelRecvSn;

    /**
     * 原送往渠道金额
     */
    @Column(name = "org_channel_amount")
    private Long orgChannelAmount;

    /**
     * 原送往渠道时间
     */
    @Column(name = "org_channel_send_time")
    private Date orgChannelSendTime;

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
