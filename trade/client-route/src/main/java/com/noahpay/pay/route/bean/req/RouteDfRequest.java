package com.noahpay.pay.route.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 路由参数
 *
 * @author chenliang
 */
@Setter
@Getter
public class RouteDfRequest extends RouteRequest implements java.io.Serializable {
    /**
     * 交易金额(分)
     */
    @NotNull(message = "交易金额不能为空")
    private Long orderAmount;
    /**
     * 地区码
     */
    private Integer city;
}
