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
 * 通道扩展参数实体
 * 表名 channel_ext_param
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "channel_ext_param")
public class ChannelExtParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

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
     * 通道属性
     */
    @Column(name = "param_key")
    private String paramKey;

    /**
     * 通道属性值
     */
    @Column(name = "param_value")
    private String paramValue;

    /**
     * 备注
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
