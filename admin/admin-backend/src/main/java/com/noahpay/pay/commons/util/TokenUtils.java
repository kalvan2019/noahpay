package com.noahpay.pay.commons.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;


public class TokenUtils {

    public static String getToken(HttpServletRequest request) {
        return request.getHeader("Access-Token");
    }

    public static String getToken() {
        HttpServletRequest requestWrapper = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return requestWrapper.getHeader("Access-Token");
    }

    public static String createToken(String userCode) {
        return SecureUtil.md5(userCode + UUID.randomUUID().toString());
    }

    public static Date createTokenExpire() {
        return DateUtil.offsetDay(new Date(), 20);
    }

}
