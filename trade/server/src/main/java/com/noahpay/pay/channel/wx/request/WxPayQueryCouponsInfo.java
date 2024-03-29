package com.noahpay.pay.channel.wx.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 查询代金券信息业务请求参数
 */
@Getter
@Setter
public class WxPayQueryCouponsInfo implements Serializable {

    /**
     * 代金券id
     */
    private String coupon_id;
    /**
     * Openid信息，用户在appid下的openid
     */
    private String openid;
    /**
     * 代金劵对应的批次号
     */
    private String stock_id;
    /**
     * 操作员帐号, 默认为商户号，可在商户平台配置操作员对应的api权限
     */
    private String op_user_id;
    /**
     * 微信支付分配的终端设备号
     */
    private String device_info;
}
