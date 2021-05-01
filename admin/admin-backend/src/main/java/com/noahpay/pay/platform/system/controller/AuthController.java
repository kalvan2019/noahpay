package com.noahpay.pay.platform.system.controller;


import com.kalvan.admin.annotation.TokenCheck;
import com.noahpay.pay.platform.system.service.AuthService;
import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.context.RequestContextHolder;
import com.kalvan.client.model.WebResponse;
import com.noahpay.pay.commons.constant.AdminContextCacheKey;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.vo.system.UserInfoVO;
import com.noahpay.pay.commons.vo.system.UserVO;
import com.kalvan.admin.log.LogType;
import com.kalvan.admin.annotation.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * 登录鉴权接口
 *
 * @author kalvan
 * @date 2020/7/26
 **/
@Slf4j
@SysLog(group = LogGroup.AUTH)
@RestController
@RequestMapping("auth")
public class AuthController {
    @Resource
    private AuthService authService;

    @SysLog(remark = "用户登录", type = LogType.LOGIN)
    @TokenCheck(required = false)
    @PostMapping("login")
    public WebResponse login(@NotBlank(message = "登录账号不能为空") String username,
                             @NotBlank(message = "登录密码不能为空") String password) {
        try {
            //初始化登录用户
            RequestContextHolder.getContext().put(AdminContextCacheKey.USER_NAME, username);
            UserVO userVO = authService.login(username, password);
            return WebResponse.buildSuccess().putData(userVO);
        } catch (Exception e) {
            return WebResponse.buildResult(CommonReturnCode.ERROR.formatMessage(e.getMessage()));
        }
    }

    @PostMapping("info")
    public WebResponse info(String systemCode) {
        if (StringUtils.isBlank(systemCode)) {
            //默认为platform
            systemCode = "platform";
        }
        UserVO userVO = (UserVO) RequestContextHolder.getContext().get(AdminContextCacheKey.USER);
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setCode(authService.selectCodeByUserId(systemCode, userVO.getId()));
        userInfoVO.setPermissions(authService.selectMenuByUserId(systemCode, userVO.getId()));
        userInfoVO.setUser(userVO);
        userInfoVO.setSystemCode(systemCode);
        if (userInfoVO.getCode().isEmpty()) {
            log.error("{}无权限访问", userVO.getUsername());
            return WebResponse.buildResult(CommonReturnCode.REQUEST_NOT_PERMISSION);
        }
        return WebResponse.buildSuccess().putData(userInfoVO);
    }

    @SysLog(remark = "用户注销登录", type = LogType.LOGIN)
    @TokenCheck(required = false)
    @PostMapping("logout")
    public WebResponse logout() {
        return WebResponse.buildSuccess();
    }

}
