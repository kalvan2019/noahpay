package com.noahpay.pay.commons.db.platform.model;

import com.kalvan.db.mybatis.annotation.ShardingUk;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenliang
 */
@Getter
@Setter
@Table(name = "role_menu")
public class RoleMenu {
    @ShardingUk
    @Id
    @Column(name = "role_id")
    private Long roleId;


    @ShardingUk
    @Id
    @Column(name = "menu_id")
    private Long menuId;
}
