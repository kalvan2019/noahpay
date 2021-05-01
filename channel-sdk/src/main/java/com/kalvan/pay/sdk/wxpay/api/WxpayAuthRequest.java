package com.kalvan.pay.sdk.wxpay.api;

import java.util.Map;

/**
 * 微信授权请求接口
 */
public interface WxpayAuthRequest<T extends WxpayAuthResponse> {

    /**
     * 服务域名
     *
     * @return 服务域名
     */
    public String serverDomain();

    /**
     * 获取所有的Key-Value形式的文本请求参数集合。其中：
     * <ul>
     * <li>Key: 请求参数名</li>
     * <li>Value: 请求参数值</li>
     * </ul>
     *
     * @return 文本请求参数集合
     */
    public Map<String, String> getTextParams();

    /**
     * 得到当前API的响应结果类型
     *
     * @return 响应类型
     */
    public Class<T> getResponseClass();

}