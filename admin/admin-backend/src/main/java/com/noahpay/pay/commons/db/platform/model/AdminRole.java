package com.noahpay.pay.commons.db.platform.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenliang
 *
 */
@Getter
@Setter
@Table(name = "admin_role")
public class AdminRole {

    @ShardingUk
    @Id
    @Column(name = "admin_id")
    private Long adminId;

    @ShardingUk
    @Id
    @Column(name = "role_id")
    private Long roleId;
}
