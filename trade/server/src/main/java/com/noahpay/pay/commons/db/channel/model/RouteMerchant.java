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
 * 商户路由配置实体
 * 表名 route_merchant
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "route_merchant")
public class RouteMerchant implements Serializable {
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
     * 支付类型
     */
    @ShardingUk
    @Column(name = "pay_type")
    private String payType;

    /**
     * 通道编号
     */
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 路由规则
     */
    @Column(name = "route_rule")
    private String routeRule;

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
