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
 * 渠道返回码实体
 * 表名 channel_return_code
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "channel_return_code")
public class ChannelReturnCode implements Serializable {
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
     * 渠道返回码
     */
    @ExcelProperty(value = "渠道返回码")
    @Column(name = "channel_return_code")
    private String channelReturnCode;

    /**
     * 渠道返回备注
     */
    @ExcelProperty(value = "渠道返回备注")
    @Column(name = "channel_result_note")
    private String channelResultNote;

    /**
     * 转换返回码
     */
    @ExcelProperty(value = "转换返回码")
    @Column(name = "return_code")
    private String returnCode;

    /**
     * 转换返回说明
     */
    @ExcelProperty(value = "转换返回说明")
    @Column(name = "return_note")
    private String returnNote;

    /**
     * 交易状态
     */
    @ExcelProperty(value = "交易状态",converter = IntegerStringConverter.class)
    @Dict(DictType.TRANS_STATE)
    @Column(name = "trans_state")
    private Integer transState;

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
