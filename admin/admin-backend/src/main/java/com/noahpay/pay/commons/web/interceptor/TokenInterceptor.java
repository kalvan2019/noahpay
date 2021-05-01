package com.noahpay.pay.commons.web.interceptor;

import com.kalvan.admin.annotation.TokenCheck;
import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.context.RequestContextHolder;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminContextCacheKey;
import com.noahpay.pay.commons.util.TokenUtils;
import com.noahpay.pay.platform.system.service.AuthService;
import com.noahpay.pay.commons.vo.system.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 检查用户登录状态
 *
 * @author chenliang
 */
@Component
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Resource
    AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TokenCheck tokenCheck;
        String token = TokenUtils.getToken(request);
        if (handler instanceof HandlerMethod) {
            tokenCheck = ((HandlerMethod) handler).getMethod().getAnnotation(TokenCheck.class);
        }else{
            return true;
        }
        //tokenCheck没标识注解默认为需要检查
        if ((tokenCheck == null || tokenCheck.required()) && StringUtils.isBlank(token)) {
            throw new BizException(CommonReturnCode.REQUEST_NO_LOGIN, false);
        }
        //查看会话token是否失效
        if (StringUtils.isNotBlank(token)) {
            UserVO userVO = authService.checkToken(token);
            if (userVO == null) {
                throw new BizException(CommonReturnCode.REQUEST_NO_LOGIN, false);
            }
            //扩展当前登录用户
            RequestContextHolder.getContext().put(AdminContextCacheKey.USER_NAME, userVO.getUsername());
            RequestContextHolder.getContext().put(AdminContextCacheKey.USER, userVO);
        }
        return super.preHandle(request, response, handler);
    }
}
