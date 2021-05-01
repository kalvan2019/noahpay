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
 * 商户信息实体
 * 表名 merchant_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "merchant_info")
public class MerchantInfo implements Serializable {
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
     * 商户号
     */
    @ExcelProperty(value = "商户号",converter = LongStringConverter.class)
    @Column(name = "merchant_no")
    private Long merchantNo;

    /**
     * 商户名
     */
    @ExcelProperty(value = "商户名")
    @Column(name = "merchant_name")
    private String merchantName;

    /**
     * 商户客户号
     */
    @ExcelProperty(value = "商户客户号",converter = LongStringConverter.class)
    @Column(name = "cust_no")
    private Long custNo;

    /**
     * 营业执照注册号
     */
    @ExcelProperty(value = "营业执照注册号")
    @Column(name = "business_license_no")
    private String businessLicenseNo;

    /**
     * 结算银行帐号
     */
    @Desensitized(type = SensitiveType.BANK_CARD)
    @ExcelProperty(value = "结算银行帐号")
    @Column(name = "bank_account_no")
    private String bankAccountNo;

    /**
     * 银行户名
     */
    @Desensitized(type = SensitiveType.BANK_NAME)
    @ExcelProperty(value = "银行户名")
    @Column(name = "bank_account_name")
    private String bankAccountName;

    /**
     * 银行帐户类型
     */
    @ExcelProperty(value = "银行帐户类型",converter = IntegerStringConverter.class)
    @Dict(DictType.BANK_ACCOUNT_TYPE)
    @Column(name = "bank_account_type")
    private Integer bankAccountType;

    /**
     * 行别
     */
    @ExcelProperty(value = "行别")
    @Dict(DictType.BANK_TYPE)
    @Column(name = "bank_type")
    private String bankType;

    /**
     * 行名
     */
    @ExcelProperty(value = "行名")
    @Column(name = "bank_name")
    private String bankName;

    /**
     * 登录密码
     */
    @ExcelProperty(value = "登录密码")
    @Column(name = "login_password")
    private String loginPassword;

    /**
     * 支付密码
     */
    @ExcelProperty(value = "支付密码")
    @Column(name = "pay_password")
    private String payPassword;

    /**
     * 密码盐
     */
    @ExcelProperty(value = "密码盐")
    @Column(name = "salt")
    private String salt;

    /**
     * 商户状态
     */
    @ExcelProperty(value = "商户状态",converter = IntegerStringConverter.class)
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
