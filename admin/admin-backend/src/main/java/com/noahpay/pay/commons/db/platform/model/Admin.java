package com.noahpay.pay.commons.db.platform.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.kalvan.admin.annotation.Dict;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import com.kalvan.sensitive.annotation.Desensitized;
import com.kalvan.sensitive.enums.SensitiveType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 管理员实体
 * 表名 admin
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Getter
@Setter
@Table(name = "admin")
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @ExcelProperty(value = "自增id", converter = LongStringConverter.class)
    @ShardingUk
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    private Long id;

    /**
     * 登录账号
     */
    @ExcelProperty(value = "登录账号")
    @Column(name = "user_code")
    private String userCode;

    /**
     * 管理员名称
     */
    @ExcelProperty(value = "管理员名称")
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 密码盐
     */
    @Column(name = "salt")
    private String salt;

    /**
     * 手机号码
     */
    @Desensitized(type = SensitiveType.MOBILE_PHONE)
    @ExcelProperty(value = "手机号码")
    @Column(name = "mobile")
    private String mobile;

    /**
     * 邮箱地址
     */
    @Desensitized(type = SensitiveType.EMAIL)
    @ExcelProperty(value = "邮箱地址")
    @Column(name = "email")
    private String email;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "state")
    private Integer state;

    /**
     * 系统管理员
     */
    @ExcelProperty(value = "系统管理员", converter = IntegerStringConverter.class)
    @Dict(DictType.SWITCH_STATE)
    @Column(name = "is_system")
    private Integer isSystem;

    /**
     * token
     */
    @Desensitized(type = SensitiveType.ALL)
    @Column(name = "token")
    private String token;

    /**
     * token过期时间
     */
    @Column(name = "token_expire")
    private Date tokenExpire;

    /**
     * 登录次数
     */
    @ExcelProperty(value = "登录次数", converter = IntegerStringConverter.class)
    @Column(name = "login_count")
    private Integer loginCount;

    /**
     * 错误次数
     */
    @ExcelProperty(value = "错误次数", converter = IntegerStringConverter.class)
    @Column(name = "login_error_count")
    private Integer loginErrorCount;

    /**
     * 锁定不允许登录时间
     */
    @ExcelProperty(value = "锁定登录时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "locked_expired_time")
    private Date lockedExpiredTime;

    /**
     * 最后登录时间
     */
    @ExcelProperty(value = "最后登录时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 最后登录ip
     */
    @ExcelProperty(value = "最后登录ip")
    @Column(name = "last_login_ip")
    private String lastLoginIp;

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

    /**
     * 新增和修改使用
     */
    @Transient
    private Long[] roleId;

    /**
     * 查询返回使用
     */
    @Transient
    private List<Role> roles;
}
