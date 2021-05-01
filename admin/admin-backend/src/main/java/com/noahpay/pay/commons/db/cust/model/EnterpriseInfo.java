package com.noahpay.pay.commons.db.cust.model;

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
 * 企业客户信息实体
 * 表名 enterprise_info
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "enterprise_info")
public class EnterpriseInfo implements Serializable {
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
     * 营业执照注册号
     */
    @ExcelProperty(value = "营业执照注册号")
    @Column(name = "business_license_no")
    private String businessLicenseNo;

    /**
     * 经营范围
     */
    @ExcelProperty(value = "经营范围")
    @Column(name = "business_scope")
    private String businessScope;

    /**
     * 年营业额
     */
    @ExcelProperty(value = "年营业额",converter = LongStringConverter.class)
    @NumberConvert(value = 100)
    @Column(name = "business_amount")
    private Long businessAmount;

    /**
     * 营业执照有效期
     */
    @ExcelProperty(value = "营业执照有效期",converter = IntegerStringConverter.class)
    @Column(name = "business_license_expiry")
    private Integer businessLicenseExpiry;

    /**
     * 企业名
     */
    @ExcelProperty(value = "企业名")
    @Column(name = "company_name")
    private String companyName;

    /**
     * 营业执照所在地
     */
    @ExcelProperty(value = "营业执照所在地")
    @Column(name = "company_address")
    private String companyAddress;

    /**
     * 行业
     */
    @ExcelProperty(value = "行业",converter = IntegerStringConverter.class)
    @Dict(DictType.INDUSTRY)
    @Column(name = "industry")
    private Integer industry;

    /**
     * 企业网站
     */
    @ExcelProperty(value = "企业网站")
    @Column(name = "website")
    private String website;

    /**
     * 企业固定电话
     */
    @ExcelProperty(value = "企业固定电话")
    @Column(name = "telephone")
    private String telephone;

    /**
     * 企业状态
     */
    @ExcelProperty(value = "企业状态",converter = IntegerStringConverter.class)
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
