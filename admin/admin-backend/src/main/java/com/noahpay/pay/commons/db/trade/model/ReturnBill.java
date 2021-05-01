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
 * 退票流水实体
 * 表名 return_bill
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "return_bill")
public class ReturnBill implements Serializable {
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
     * 交易日期
     */
    @Column(name = "trans_date")
    private Integer transDate;

    /**
     * 订单号
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 订单金额
     */
    @Column(name = "order_amount")
    private Long orderAmount;

    /**
     * 商户号
     */
    @Column(name = "merchant_no")
    private Long merchantNo;

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
     * 子商户手续费
     */
    @Column(name = "sub_merchant_fee")
    private Long subMerchantFee;

    /**
     * 交易会计日期
     */
    @Column(name = "trans_account_date")
    private Integer transAccountDate;

    /**
     * 退票日期
     */
    @Column(name = "return_ticket_date")
    private Integer returnTicketDate;

    /**
     * 退票备注
     */
    @Column(name = "remark")
    private String remark;

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
