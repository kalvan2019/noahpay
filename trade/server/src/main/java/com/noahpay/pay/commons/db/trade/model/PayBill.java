package com.noahpay.pay.commons.db.trade.model;

import com.kalvan.db.mybatis.annotation.ShardingTableKey;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 支付订单实体
 * 表名 pay_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "pay_bill")
public class PayBill implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Long id;

    /**
     * 交易流水号
     */
    @ShardingUk
    @Column(name = "trans_id")
    private String transId;

    /**
     * 交易类型
     */
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 商户订单号
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 商户订单日期
     */
    @Column(name = "order_date")
    private Integer orderDate;

    /**
     * 商品描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 附加数据
     */
    @Column(name = "attach")
    private String attach;

    /**
     * 订单金额
     */
    @Column(name = "amount")
    private Long amount;

    /**
     * 币种
     */
    @Column(name = "currency")
    private String currency;

    /**
     * 生效时间
     */
    @Column(name = "time_start")
    private Date timeStart;

    /**
     * 失效时间
     */
    @Column(name = "time_expire")
    private Date timeExpire;

    /**
     * 通知地址
     */
    @Column(name = "notify_url")
    private String notifyUrl;

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
     * 商户手续费
     */
    @Column(name = "merchant_fee")
    private Long merchantFee;

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
     * 付款方信息
     */
    @Column(name = "payer_info")
    private String payerInfo;

    /**
     * 场景信息
     */
    @Column(name = "scene_info")
    private String sceneInfo;

    /**
     * 通道编号
     */
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 通道商户号
     */
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 通道子商户号
     */
    @Column(name = "channel_sub_merchant_no")
    private String channelSubMerchantNo;

    /**
     * 通道金额
     */
    @Column(name = "channel_amount")
    private Long channelAmount;

    /**
     * 通道订单号
     */
    @Column(name = "channel_send_sn")
    private String channelSendSn;

    /**
     * 送往通道时间
     */
    @Column(name = "channel_send_time")
    private Date channelSendTime;

    /**
     * 通道流水号
     */
    @Column(name = "channel_recv_sn")
    private String channelRecvSn;

    /**
     * 通道返回时间
     */
    @Column(name = "channel_recv_time")
    private Date channelRecvTime;

    /**
     * 通道返回扩展数据
     */
    @Column(name = "channel_recv_ext")
    private String channelRecvExt;

    /**
     * 通道预支付标识
     */
    @Column(name = "channel_prepay_id")
    private String channelPrepayId;

    /**
     * 通道二维码
     */
    @Column(name = "channel_code_url")
    private String channelCodeUrl;

    /**
     * 通道支付链接
     */
    @Column(name = "channel_web_url")
    private String channelWebUrl;

    /**
     * 通道会计日期
     */
    @Column(name = "channel_account_date")
    private Integer channelAccountDate;

    /**
     * 通道返回码
     */
    @Column(name = "channel_result_code")
    private String channelResultCode;

    /**
     * 通道返回备注
     */
    @Column(name = "channel_result_note")
    private String channelResultNote;

    /**
     * 支付方式
     */
    @Column(name = "pay_type")
    private String payType;

    /**
     * 返回码
     */
    @Column(name = "pay_result_code")
    private String payResultCode;

    /**
     * 返回描述
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
