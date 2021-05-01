package com.noahpay.pay.commons.constant;

import com.kalvan.client.system.SystemUtil;

/**
 * @author kalvan
 */
public class RedisCacheConstants {

    public static final String SYSTEM = SystemUtil.constants.getName();

    /**
     * 同一用户名5s登录次数达到10次，则限制该用户1小时内不让登录
     */
    public static final String LOGIN_RISK_USER = SYSTEM + ":login_risk_user:";
    /**
     * 同一用户名一天内登录次数达到200次，则限制该用户当天内不让登录
     */
    public static final String LOGIN_RISK_USER_DAY = SYSTEM + ":login_risk_user_day:";
    /**
     * 同一IP5s登录次数达到200次，则限制该IP1小时内不让登录
     */
    public static final String LOGIN_RISK_IP = SYSTEM + ":login_risk_ip:";
    /**
     * 重置密码缓存前缀
     */
    public static final String PWD_RESET = SYSTEM + ":pwd_reset:";
    /**
     * 重置密码验证短信验证码缓存前缀
     */
    public static final String PWD_RESET_MSG = SYSTEM + ":pwd_reset_msg:";
}
