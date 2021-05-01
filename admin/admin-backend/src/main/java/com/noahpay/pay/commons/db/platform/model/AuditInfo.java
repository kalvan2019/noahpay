package com.noahpay.pay.commons.db.platform.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
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
 * 审核信息实体
 * 表名 audit_info
 *
 * @author kalvan.tools:kalvan.tools
 */
@Getter
@Setter
@Table(name = "audit_info")
public class AuditInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @ShardingUk
    @Column(name = "id")
    private Long id;

    /**
     * 数据类型
     */
    @ExcelProperty(value = "数据类型")
    @Dict(DictType.LOG_GROUP)
    @Column(name = "data_type")
    private String dataType;

    /**
     * 变更前数据
     */
    @ExcelProperty(value = "变更前数据")
    @Column(name = "data_old")
    private String dataOld;

    /**
     * 变更后数据
     */
    @ExcelProperty(value = "变更后数据")
    @Column(name = "data_new")
    private String dataNew;

    /**
     * 发起人
     */
    @ExcelProperty(value = "发起人")
    @Column(name = "applicant")
    private String applicant;

    /**
     * 审核类型
     */
    @ExcelProperty(value = "审核类型",converter = IntegerStringConverter.class)
    @Dict(DictType.LOG_TYPE)
    @Column(name = "audit_type")
    private Integer auditType;

    /**
     * 审核说明
     */
    @ExcelProperty(value = "审核说明")
    @Column(name = "audit_note")
    private String auditNote;

    /**
     * 审核人
     */
    @ExcelProperty(value = "审核人")
    @Column(name = "audit_user")
    private String auditUser;

    /**
     * 审核备注
     */
    @ExcelProperty(value = "审核备注")
    @Column(name = "audit_remark")
    private String auditRemark;

    /**
     * 审核状态
     */
    @ExcelProperty(value = "审核状态",converter = IntegerStringConverter.class)
    @Dict(DictType.AUDIT_STATE)
    @Column(name = "audit_state")
    private Integer auditState;

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
