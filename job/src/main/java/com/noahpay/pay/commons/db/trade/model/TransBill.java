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
 * 交易流水实体
 * 表名 trans_bill
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "trans_bill")
public class TransBill implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 交易流水号
     */
    @ShardingUk
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
     * 订单说明
     */
    @Column(name = "order_note")
    private String orderNote;
    /**
     * 订单金额
     */
    @Column(name = "order_amount")
    private Long orderAmount;
    /**
     * 域名
     */
    @Column(name = "order_domain")
    private String orderDomain;
    /**
     * 商户端IP
     */
    @Column(name = "order_ip")
    private String orderIp;
    /**
     * 设备信息
     */
    @Column(name = "order_device_info")
    private String orderDeviceInfo;
    /**
     * 行业类别
     */
    @Column(name = "order_mcc")
    private Integer orderMcc;
    /**
     * 城市代码
     */
    @Column(name = "order_city")
    private Integer orderCity;
    /**
     * 未支付失效时间
     */
    @Column(name = "order_expiry_time")
    private Date orderExpiryTime;
    /**
     * 页面通知地址
     */
    @Column(name = "notify_pg_url")
    private String notifyPgUrl;
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
     * 商户名
     */
    @Column(name = "merchant_name")
    private String merchantName;
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
     * 商户手续费
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
     * 子商户手续费
     */
    @Column(name = "sub_merchant_fee")
    private Long subMerchantFee;
    /**
     * 消费者手续费
     */
    @Column(name = "consumer_fee")
    private Long consumerFee;
    /**
     * 结算金额
     */
    @Column(name = "settlement_amount")
    private Long settlementAmount;
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
     * 支付模式
     */
    @Column(name = "pay_model")
    private Integer payModel;
    /**
     * 支付类型
     */
    @Column(name = "pay_type")
    private Integer payType;
    /**
     * 支付流水号
     */
    @Column(name = "pay_id")
    private Long payId;
    /**
     * 支付路由信息
     */
    @Column(name = "pay_route_info")
    private String payRouteInfo;
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
     * 交易状态
     */
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
