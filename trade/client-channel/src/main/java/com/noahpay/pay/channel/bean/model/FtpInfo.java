package com.noahpay.pay.channel.bean.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * ftp登陆配置信息
 *
 * @author chenliang
 */
@Setter
@Getter
public class FtpInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 服务器地址名称
     **/
    @NotBlank(message = "ftp server不能为空")
    String server;
    /**
     * 端口号
     **/
    int port;
    /**
     * 用户名称
     **/
    @NotBlank(message = "ftp username不能为空")
    String username;
    /**
     * 密码
     **/
    @NotBlank(message = "ftp password不能为空")
    String password;
}