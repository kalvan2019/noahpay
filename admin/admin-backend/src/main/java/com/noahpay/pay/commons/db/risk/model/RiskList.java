package com.noahpay.pay.commons.db.risk.model;

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
 * 风险事件记录实体
 * 表名 risk_list
 *
 * @author kalvan.tools:kalvan
 */
@Getter
@Setter
@Table(name = "risk_list")
public class RiskList implements Serializable {
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
     * 关联id
     */
    @ExcelProperty(value = "关联id")
    @Column(name = "ref_trans_id")
    private String refTransId;

    /**
     * 风控等级
     */
    @ExcelProperty(value = "风控等级",converter = IntegerStringConverter.class)
    @Dict(DictType.RISK_LEVEL)
    @Column(name = "risk_level")
    private Integer riskLevel;

    /**
     * 被风控参数
     */
    @ExcelProperty(value = "被风控参数")
    @Column(name = "risk_params")
    private String riskParams;

    /**
     * 被风控原因
     */
    @ExcelProperty(value = "被风控原因")
    @Column(name = "risk_reason")
    private String riskReason;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

}
