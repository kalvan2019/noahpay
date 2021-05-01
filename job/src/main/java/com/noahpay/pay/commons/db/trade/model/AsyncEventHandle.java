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
 * 异步事务控制实体
 * 表名 async_event_handle
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "async_event_handle")
public class AsyncEventHandle implements Serializable {
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
     * 支付流水号
     */
    @Column(name = "pay_id")
    private Long payId;
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
     * 账户号
     */
    @Column(name = "account_no")
    private Long accountNo;
    /**
     * 事件类型
     */
    @Column(name = "event_type")
    private Integer eventType;
    /**
     * 事件内容json
     */
    @Column(name = "event_content")
    private String eventContent;
    /**
     * 执行策略
     */
    @Column(name = "strategy_type")
    private Integer strategyType;
    /**
     * 支付网关返回码
     */
    @Column(name = "result_code")
    private String resultCode;
    /**
     * 支付网关备注
     */
    @Column(name = "result_note")
    private String resultNote;
    /**
     * 执行次数
     */
    @Column(name = "deal_times")
    private Integer dealTimes;
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
