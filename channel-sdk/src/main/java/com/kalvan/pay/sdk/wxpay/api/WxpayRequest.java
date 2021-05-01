package com.kalvan.pay.sdk.wxpay.api;

import java.util.Map;

/**
 * 支付请求接口
 */
public interface WxpayRequest<T extends WxpayResponse> {

    /**
     * 返回服务域名
     *
     * @return 服务域名
     */
    String serverDomain();

    /**
     * 是否需要证书
     *
     * @return true/false
     */
    boolean isNeedCert();

    /**
     * 是否验证签名
     *
     * @return true/false
     */
    boolean isCheckSign();

    /**
     * 返回通知地址
     *
     * @return 通知地址
     */
    String getNotifyUrl();

    /**
     * 设置通知地址
     *
     * @param notifyUrl
     */
    void setNotifyUrl(String notifyUrl);

    /**
     * 获取所有的Key-Value形式的文本请求参数集合。其中：
     * <ul>
     * <li>Key: 请求参数名</li>
     * <li>Value: 请求参数值</li>
     * </ul>
     *
     * @return 文本请求参数集合
     */
    Map<String, String> getTextParams();

    /**
     * 得到当前API的响应结果类型
     *
     * @return 响应类型
     */
    Class<T> getResponseClass();

}
