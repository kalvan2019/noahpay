package com.noahpay.pay.commons.web.interceptor;

import com.kalvan.admin.annotation.Permission;
import com.noahpay.pay.commons.util.TokenUtils;
import com.noahpay.pay.platform.system.service.AuthService;
import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 检查权限
 *
 * @author chenliang
 */
@Component
@Slf4j
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Resource
    AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            //获取方法上的权限注解
            Permission methodPermission = ((HandlerMethod) handler).getMethod().getAnnotation(Permission.class);
            List<String> needPermission = new ArrayList<>();
            //获取方法上的权限
            if (methodPermission != null) {
                needPermission.addAll(Arrays.asList(methodPermission.value()));
            }
            if (needPermission.size() > 0) {
                String token = TokenUtils.getToken(request);
                if (StringUtils.isBlank(token)) {
                    throw new BizException(CommonReturnCode.REQUEST_NO_LOGIN);
                }
                if (!authService.checkPermission(token, needPermission)) {
                    throw new BizException(CommonReturnCode.REQUEST_NOT_PERMISSION);
                }
            }
        }
        return super.preHandle(request, response, handler);
    }
}
