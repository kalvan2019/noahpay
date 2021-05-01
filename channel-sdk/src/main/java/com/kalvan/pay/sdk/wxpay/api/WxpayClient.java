package com.kalvan.pay.sdk.wxpay.api;

/**
 * wxpay client
 *
 * @version 1.3.0
 */
public interface WxpayClient {

    /**
     * 获取客户端名称
     *
     * @return
     */
	String getName();

    /**
     * 获取服务地址
     *
     * @return
     */
	String getServerUrl();

    /**
     * 请求
     *
     * @param <T>
     * @param request
     * @return
     * @throws WxpayApiException
     */
	<T extends WxpayResponse> T execute(WxpayRequest<T> request) throws WxpayApiException;

    /**
     * 通知
     *
     * @param <T>
     * @param request
     * @return
     * @throws WxpayApiException
     */
	<T extends WxpayResponse> T notify(WxpayRequest<T> request) throws WxpayApiException;

    /**
     * 授权
     *
     * @param <T>
     * @param request
     * @return
     * @throws WxpayApiException
     */
	<T extends WxpayAuthResponse> T auth(WxpayAuthRequest<T> request) throws WxpayApiException;

    /**
     * js-sdk
     *
     * @param <T>
     * @param request
     * @return
     * @throws WxpayApiException
     */
	<T extends WxpayJsApiResponse> T jsApi(WxpayJsApiRequest<T> request) throws WxpayApiException;

}
