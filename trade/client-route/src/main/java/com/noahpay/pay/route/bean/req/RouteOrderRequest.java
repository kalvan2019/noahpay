package com.noahpay.pay.route.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 路由参数
 *
 * @author chenliang
 */
@Setter
@Getter
public class RouteOrderRequest extends RouteRequest implements java.io.Serializable {
    /**
     * 收银台路由
     * 收银台路由只需要返回列表，不需要具体到交易商户
     */
    private boolean cashierRoute;
    /**
     * 指定路由集
     * 协议支付或其他需要指定通道编号场景使用
     */
    private List<Integer> channelNoList;
    /**
     * 交易金额(分)
     */
    @NotNull(message = "交易金额不能为空")
    private Long orderAmount;
    /**
     * 地区码
     */
    private Integer city;
    /**
     * 业务代码
     */
    private Integer mcc;
    /**
     * 银行账号
     * 针对一个通道子商户号在单位时间内同一卡号只能消费一次
     */
    private String bankAccountNo;
}
