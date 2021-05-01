package com.noahpay.pay.commons.db.channel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.admin.annotation.NumberConvert;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 渠道商户信息实体
 * 表名 channel_merchant_pool
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "channel_merchant_pool")
public class ChannelMerchantPool implements Serializable {
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
     * 渠道商户号
     */
    @ExcelProperty(value = "渠道商户号")
    @Column(name = "channel_merchant_no")
    private String channelMerchantNo;

    /**
     * 渠道子级商户号
     */
    @ExcelProperty(value = "渠道子级商户号")
    @Column(name = "channel_sub_merchant_no")
    private String channelSubMerchantNo;

    /**
     * 子商户名称
     */
    @ExcelProperty(value = "子商户名称")
    @Column(name = "channel_sub_merchant_name")
    private String channelSubMerchantName;

    /**
     * 商户号
     */
    @ExcelProperty(value = "商户号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 城市代码
     */
    @ExcelProperty(value = "城市代码",converter = IntegerStringConverter.class)
    @Column(name = "city")
    private Integer city;

    /**
     * 行业类别
     */
    @ExcelProperty(value = "行业类别",converter = IntegerStringConverter.class)
    @Column(name = "mcc")
    private Integer mcc;

    /**
     * 开始时间
     */
    @ExcelProperty(value = "开始时间")
    @Column(name = "begin_time")
    private String beginTime;

    /**
     * 结束时间
     */
    @ExcelProperty(value = "结束时间")
    @Column(name = "end_time")
    private String endTime;

    /**
     * 单笔金额下限
     */
    @ExcelProperty(value = "单笔金额下限",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "limit_min_amount")
    private Long limitMinAmount;

    /**
     * 单笔金额上限
     */
    @ExcelProperty(value = "单笔金额上限",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "limit_max_amount")
    private Long limitMaxAmount;

    /**
     * 日使用金额
     */
    @ExcelProperty(value = "日使用金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "day_use_amount")
    private Long dayUseAmount;

    /**
     * 日限额
     */
    @ExcelProperty(value = "日限额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "day_limit_amount")
    private Long dayLimitAmount;

    /**
     * 月使用金额
     */
    @ExcelProperty(value = "月使用金额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "month_use_amount")
    private Long monthUseAmount;

    /**
     * 月金额限额
     */
    @ExcelProperty(value = "月金额限额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "month_limit_amount")
    private Long monthLimitAmount;

    /**
     * 商户状态
     */
    @ExcelProperty(value = "商户状态",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "state")
    private Integer state;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @Column(name = "remark")
    private String remark;

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
