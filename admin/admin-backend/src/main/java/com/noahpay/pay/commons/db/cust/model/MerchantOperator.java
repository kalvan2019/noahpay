package com.noahpay.pay.commons.db.cust.model;

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
 * 商户操作员表实体
 * 表名 merchant_operator
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "merchant_operator")
public class MerchantOperator implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id",converter = LongStringConverter.class)
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
     * 操作员账号
     */
    @ExcelProperty(value = "操作员账号",converter = LongStringConverter.class)
    @Column(name = "operator_no")
    private Long operatorNo;

    /**
     * 操作员姓名
     */
    @ExcelProperty(value = "操作员姓名")
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 登录密码
     */
    @ExcelProperty(value = "登录密码")
    @Column(name = "login_pwd")
    private String loginPwd;

    /**
     * 盐值
     */
    @ExcelProperty(value = "盐值")
    @Column(name = "salt")
    private String salt;

    /**
     * 操作员状态:0.生效；1.冻结；
     */
    @ExcelProperty(value = "操作员状态:0.生效；1.冻结；",converter = IntegerStringConverter.class)
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
