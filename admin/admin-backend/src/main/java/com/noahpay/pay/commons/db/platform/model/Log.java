package com.noahpay.pay.commons.db.platform.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.integer.IntegerStringConverter;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.kalvan.admin.annotation.Dict;
import com.kalvan.admin.dict.SystemDictType;
import com.noahpay.pay.commons.constant.DictType;
import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志记录实体
 * 表名 log
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Getter
@Setter
@Table(name = "log")
public class Log implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "自增id",converter = LongStringConverter.class)
    @ShardingUk
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 操作人
     */
    @ExcelProperty(value = "操作人")
    @Column(name = "log_user")
    private String logUser;

    /**
     * 日志来源
     */
    @ExcelProperty(value = "日志来源")
    @Dict(SystemDictType.SYSTEM_CODE)
    @Column(name = "system_code")
    private String systemCode;

    /**
     * 操作类型
     */
    @ExcelProperty(value = "日志分组")
    @Dict(DictType.LOG_GROUP)
    @Column(name = "log_group")
    private String logGroup;

    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型",converter = IntegerStringConverter.class)
    @Dict(DictType.LOG_TYPE)
    @Column(name = "log_type")
    private Integer logType;

    /**
     * 操作路径
     */
    @ExcelProperty(value = "操作路径 ")
    @Column(name = "log_action")
    private String logAction;

    /**
     * 操作说明
     */
    @ExcelProperty(value = "操作说明")
    @Column(name = "log_remark")
    private String logRemark;

    /**
     * 操作内容明细
     */
    @ExcelProperty(value = "操作内容明细")
    @Column(name = "log_detail")
    private String logDetail;

    /**
     * 操作前内容明细
     */
    @ExcelProperty(value = "操作前内容明细")
    @Column(name = "log_old_detail")
    private String logOldDetail;

    /**
     * 被操作者
     */
    @ExcelProperty(value = "被操作者")
    @Column(name = "log_operated")
    private String logOperated;

    /**
     * 操作ip
     */
    @ExcelProperty(value = "操作ip")
    @Column(name = "log_ip")
    private String logIp;

    /**
     * 操作时间
     */
    @ShardingUk
    @ExcelProperty(value = "操作时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "log_time")
    private Date logTime;

}
