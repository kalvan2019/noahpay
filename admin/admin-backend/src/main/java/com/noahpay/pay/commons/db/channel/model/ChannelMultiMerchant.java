package com.noahpay.pay.commons.db.channel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 渠道收款商户绑定实体
 * 表名 channel_multi_merchant
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "channel_multi_merchant")
public class ChannelMultiMerchant implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @ExcelProperty(value = "自增id",converter = LongStringConverter.class)
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 渠道编号
     */
    @ExcelProperty(value = "渠道编号",converter = IntegerStringConverter.class)
    @Dict(DictType.CHANNEL_NO)
    @Column(name = "channel_no")
    private Integer channelNo;

    /**
     * 支付类型
     */
    @ExcelProperty(value = "支付类型",converter = IntegerStringConverter.class)
    @Dict(DictType.PAY_TYPE)
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 商户号
     */
    @ExcelProperty(value = "商户号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 渠道商户号
     */
    @ExcelProperty(value = "渠道商户号")
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 渠道商户名称
     */
    @ExcelProperty(value = "渠道商户名称")
    @Column(name = "channel_merchant_name")
    private String channelMerchantName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @Column(name = "remark")
    private String remark;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "state")
    private Integer state;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;

}
