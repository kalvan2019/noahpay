package com.noahpay.pay.commons.db.cust.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import com.kalvan.sensitive.annotation.Desensitized;
import com.kalvan.sensitive.enums.SensitiveType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息实体
 * 表名 cust_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "cust_info")
public class CustInfo implements Serializable {
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
     * 客户号
     */
    @ExcelProperty(value = "客户号",converter = LongStringConverter.class)
    @Column(name = "cust_no")
    private Long custNo;

    /**
     * 证件号码
     */
    @Desensitized(type = SensitiveType.ID_CARD)
    @ExcelProperty(value = "证件号码")
    @Column(name = "certificate_no")
    private String certificateNo;

    /**
     * 客户名称
     */
    @ExcelProperty(value = "客户名称")
    @Column(name = "certificate_name")
    private String certificateName;

    /**
     * 证件类型
     */
    @ExcelProperty(value = "证件类型",converter = IntegerStringConverter.class)
    @Dict(DictType.CERTIFICATE_TYPE)
    @Column(name = "certificate_type")
    private Integer certificateType;

    /**
     * 证件地址
     */
    @Desensitized(type = SensitiveType.ADDRESS)
    @ExcelProperty(value = "证件地址")
    @Column(name = "certificate_address")
    private String certificateAddress;

    /**
     * 证件有效日期
     */
    @ExcelProperty(value = "证件有效日期",converter = IntegerStringConverter.class)
    @Column(name = "certificate_expiry")
    private Integer certificateExpiry;

    /**
     * 客户邮箱
     */
    @Desensitized(type = SensitiveType.EMAIL)
    @ExcelProperty(value = "客户邮箱")
    @Column(name = "email")
    private String email;

    /**
     * 客户手机
     */
    @Desensitized(type = SensitiveType.MOBILE_PHONE)
    @ExcelProperty(value = "客户手机")
    @Column(name = "mobile")
    private String mobile;

    /**
     * 详细地址
     */
    @Desensitized(type = SensitiveType.ADDRESS)
    @ExcelProperty(value = "详细地址")
    @Column(name = "address")
    private String address;

    /**
     * 客户状态
     */
    @ExcelProperty(value = "客户状态",converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "state")
    private Integer state;

    /**
     * 销户时间
     */
    @ExcelProperty(value = "销户时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "close_time")
    private Date closeTime;

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
