package com.noahpay.pay.commons.db.channel.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 通道列表实体
 * 表名 channel_info
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "channel_info")
public class ChannelInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 通道编号
     */
    @ShardingUk
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 通道名称
     */
    @Column(name = "channel_name")
    private String channelName;

    /**
     * 实现类名
     */
    @Column(name = "channel_class")
    private String channelClass;

    /**
     * 通道商户号
     */
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 大商户轮询
     */
    @Column(name = "merchant_pool_convert")
    private Integer merchantPoolConvert;

    /**
     * 转换多商户
     */
    @Column(name = "multi_merchant_convert")
    private Integer multiMerchantConvert;

    /**
     * 转换发往银行流水号
     */
    @Column(name = "send_sn_convert")
    private Integer sendSnConvert;

    /**
     * 转换行别
     */
    @Column(name = "bank_type_convert")
    private Integer bankTypeConvert;

    /**
     * 转换银行账户类型
     */
    @Column(name = "bank_account_type_convert")
    private Integer bankAccountTypeConvert;

    /**
     * 转换证件类型
     */
    @Column(name = "certificate_type_convert")
    private Integer certificateTypeConvert;

    /**
     * 转换城市代码
     */
    @Column(name = "city_convert")
    private Integer cityConvert;

    /**
     * 行业类目转换
     */
    @Column(name = "mcc_convert")
    private Integer mccConvert;

    /**
     * 转换客户号类型
     */
    @Column(name = "cust_convert")
    private Integer custConvert;

    /**
     * 连接超时时间
     */
    @Column(name = "connection_timeout")
    private Integer connectionTimeout;

    /**
     * 通道最大并发数
     */
    @Column(name = "connection_max_num")
    private Integer connectionMaxNum;

    /**
     * 读超时时间
     */
    @Column(name = "read_timeout")
    private Integer readTimeout;

    /**
     * 通道发送短信
     */
    @Column(name = "sms_support")
    private Integer smsSupport;

    /**
     * 支持对账 0-支持对账;1-不支持
     */
    @Column(name = "check_support")
    private Integer checkSupport;

    /**
     * 对账时间
     */
    @Column(name = "check_time")
    private String checkTime;

    /**
     * 对账凭证
     */
    @Column(name = "check_field")
    private Integer checkField;

    /**
     * 结算周期
     */
    @Column(name = "settlement_time")
    private String settlementTime;

    /**
     * 结算账号
     */
    @Column(name = "settlement_bank_account_no")
    private String settlementBankAccountNo;

    /**
     * 结算户名
     */
    @Column(name = "settlement_bank_account_name")
    private String settlementBankAccountName;

    /**
     * 结算银行
     */
    @Column(name = "settlement_bank_type")
    private String settlementBankType;

    /**
     * 状态
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
