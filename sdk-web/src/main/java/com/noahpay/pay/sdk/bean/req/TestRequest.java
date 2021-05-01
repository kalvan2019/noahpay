package com.noahpay.pay.sdk.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * 发送明文数据
 *
 * @author chenliang
 */
@Getter
@Setter
public class TestRequest {
    /**
     * appId
     */
    @NotBlank(message = "appId不能为空")
    private String appId;
    /**
     * service
     * 可通过path获取也可以手动指定
     */
    private String service;
    /**
     * json数据明文map对象
     */
    @NotEmpty(message = "data不能为空")
    private Map data;
}
