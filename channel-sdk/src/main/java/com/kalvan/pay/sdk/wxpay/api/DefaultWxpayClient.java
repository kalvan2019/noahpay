package com.kalvan.pay.sdk.wxpay.api;

import lombok.Getter;
import lombok.Setter;

/**
 * default wxpay client
 */
@Getter
@Setter
public class DefaultWxpayClient extends AbstractWxpayClient {


    public DefaultWxpayClient(String name) {
        super(name);
    }

    public DefaultWxpayClient(String appId, String secret, int connectTimeout,
                              int readTimeout) {
        super(appId, secret, connectTimeout, readTimeout);

    }

    public DefaultWxpayClient(String appId, String mchId, String key,
                              String secret, String certFile, String format, String charset,
                              String signType, int connectTimeout, int readTimeout) {
        super(appId, mchId, key, secret, certFile, format, charset, signType,
                connectTimeout, readTimeout);

    }

    public DefaultWxpayClient(String appId, String subAppId, String mchId,
                              String subMchId, String key, String secret, String certFile,
                              String format, String charset, String signType, int connectTimeout,
                              int readTimeout) {
        super(appId, subAppId, mchId, subMchId, key, secret, certFile, format, charset,
                signType, connectTimeout, readTimeout);

    }

    public DefaultWxpayClient(String appId, String subAppId, String mchId,
                              String subMchId, String key, String secret, String certFile,
                              String format, String charset, String signType) {
        super(appId, subAppId, mchId, subMchId, key, secret, certFile, format, charset,
                signType);

    }

    public DefaultWxpayClient(String appId, String subAppId, String mchId,
                              String subMchId, String key, String subKey, String secret,
                              String certFile, String subCertFile) {
        super(appId, subAppId, mchId, subMchId, key, subKey, secret, certFile,
                subCertFile);

    }

    public DefaultWxpayClient(String appId, String mchId, String key,
                              String secret, String certFile) {
        super(appId, mchId, key, secret, certFile);

    }

    public DefaultWxpayClient(String appId, String mchId, String key) {
        super(appId, mchId, key);

    }

    public DefaultWxpayClient(String appId, String secret) {
        super(appId, secret);

    }


}
