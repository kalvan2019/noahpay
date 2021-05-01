package com.noahpay.pay.commons.db.cust.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体
 * 表名 cust_unique_code
 *
 * @author kalvan.tools:chenliang
 */
@Getter
@Setter
@Table(name = "cust_unique_code")
public class CustUniqueCode implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 分库号
     */
    @ShardingUk
    @Column(name = "part_library")
    private Integer partLibrary;

    /**
     * 分表号
     */
    @ShardingUk
    @Column(name = "part_tables")
    private Integer partTables;

    /**
     * 客户类型
     */
    @ShardingUk
    @Column(name = "cust_type")
    private String custType;

    /**
     * 当前最大编号
     */
    @Column(name = "max_code")
    private Integer maxCode;

}
