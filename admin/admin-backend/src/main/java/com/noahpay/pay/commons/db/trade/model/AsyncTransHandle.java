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
 * 交易异步处理任务表实体
 * 表名 async_trans_handle
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "async_trans_handle")
public class AsyncTransHandle implements Serializable {
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
     * 支付类型
     */
    @Dict(DictType.PAY_TYPE)
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 返回码
     */
    @Column(name = "result_code")
    private String resultCode;

    /**
     * 返回描述
     */
    @Column(name = "result_note")
    private String resultNote;

    /**
     * 执行次数
     */
    @Column(name = "deal_times")
    private Integer dealTimes;

    /**
     * 优先级
     */
    @Column(name = "priority")
    private Integer priority;

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
