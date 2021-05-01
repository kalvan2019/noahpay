package com.noahpay.pay.commons.vo.system;

import com.noahpay.pay.commons.db.platform.model.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Getter
@Setter
public class UserInfoVO {


    private UserVO user;


    private List<Menu> permissions;


    private Set<String> code;

    private String systemCode;
}
