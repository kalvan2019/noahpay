package com.noahpay.pay.commons.db.cust.model;

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
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "merchant_product_trans")
public class MerchantProductTrans implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 商户号
     */
    @ShardingUk
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 交易类型
     */
    @ShardingUk
    @Column(name = "trans_type")
    private Integer transType;

    /**
     * 单笔金额上限
     */
    @Column(name = "limit_max_amount")
    private Long limitMaxAmount;

    /**
     * 日限额
     */
    @Column(name = "day_limit_amount")
    private Long dayLimitAmount;

    /**
     * 日限笔数
     */
    @Column(name = "day_limit_number")
    private Long dayLimitNumber;

    /**
     * 月金额限额
     */
    @Column(name = "month_limit_amount")
    private Long monthLimitAmount;

    /**
     * 月笔数限额
     */
    @Column(name = "month_limit_number")
    private Long monthLimitNumber;

    /**
     * 生效日期
     */
    @Column(name = "effective_date")
    private Integer effectiveDate;

    /**
     * 失效日期
     */
    @Column(name = "expiry_date")
    private Integer expiryDate;

    /**
     * 客户协议审核
     */
    @Column(name = "sign_audit_type")
    private Integer signAuditType;

    /**
     * 签约验证手机
     */
    @Column(name = "sign_check_sms")
    private Integer signCheckSms;

    /**
     * 签约成功发短信
     */
    @Column(name = "sign_send_sms")
    private Integer signSendSms;

    /**
     * 处理方式
     */
    @Column(name = "trans_deal_type")
    private Integer transDealType;

    /**
     * 交易审核
     */
    @Column(name = "trans_audit_type")
    private Integer transAuditType;

    /**
     * 交易检查客户协议
     */
    @Column(name = "trans_check_protocol")
    private Integer transCheckProtocol;

    /**
     * 交易验证手机
     */
    @Column(name = "trans_check_sms")
    private Integer transCheckSms;

    /**
     * 交易成功发短信
     */
    @Column(name = "trans_send_sms")
    private Integer transSendSms;

    /**
     * 入网状态
     */
    @Column(name = "state")
    private Integer state;

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
