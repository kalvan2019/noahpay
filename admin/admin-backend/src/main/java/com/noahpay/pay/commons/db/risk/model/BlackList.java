package com.noahpay.pay.commons.db.risk.model;

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
 * 黑名单实体
 * 表名 black_list
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "black_list")
public class BlackList implements Serializable {
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
     * 黒名单类型
     */
    @ExcelProperty(value = "黒名单类型",converter = IntegerStringConverter.class)
    @Dict(DictType.BLACK_TYPE)
    @Column(name = "black_type")
    private Integer blackType;

    /**
     * 客户号
     */
    @ExcelProperty(value = "客户号",converter = LongStringConverter.class)
    @Column(name = "cust_no")
    private Long custNo;

    /**
     * 客户名称
     */
    @ExcelProperty(value = "客户名称")
    @Column(name = "cust_name")
    private String custName;

    /**
     * 证件号码
     */
    @Desensitized(type = SensitiveType.ID_CARD)
    @ExcelProperty(value = "证件号码")
    @Column(name = "certificate_no")
    private String certificateNo;

    /**
     * 银行帐号
     */
    @Desensitized(type = SensitiveType.BANK_CARD)
    @ExcelProperty(value = "银行帐号")
    @Column(name = "bank_account_no")
    private String bankAccountNo;

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
     * 营业执照注册号
     */
    @ExcelProperty(value = "营业执照注册号")
    @Column(name = "business_license_no")
    private String businessLicenseNo;

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
