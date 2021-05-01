package com.noahpay.pay.commons.vo.system;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户登录后返回数据
 *
 * @author chenliang
 */
@Getter
@Setter
public class UserVO {

    private Long id;

    private String username;

    private String nickName;

    private String token;

    private Date tokenExpire;

    private Date lastLoginTime;
}
