package com.noahpay.pay.route.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 路由参数
 *
 * @author chenliang
 */
@Setter
@Getter
public class RouteRequest implements java.io.Serializable {
    /**
     * 支付类型
     */
    @NotBlank(message = "支付类型不能为空")
    private String payType;
    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    private Long merchantNo;
    /**
     * 账户类型
     */
    private Integer bankAccountType;
    /**
     * 行别
     */
    private String bankType;
    /**
     * 通道商户号
     * 多商户情况使用
     */
    private String channelMerchantNo;
}
