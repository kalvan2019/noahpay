package com.noahpay.pay.channel.wx;

import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author chenliang
 */
@Getter
public class WxRequest<R extends WxMsgInfo> implements Serializable {
    /**
     * 需要证书
     * 退款资金操作需要
     */
    private boolean needCert = false;
    /**
     * 是否验证签名
     */
    private boolean checkSign = false;
    /**
     * 请求域名
     */
    private String serverDomain = WxPayConstants.DOMAIN_API_MCH;
    /**
     * 请求path
     */
    private final String path;
    /**
     * 连接超时毫秒
     */
    private int connectTimeout = 3000;
    /**
     * 读取超时毫秒
     */
    private int readTimeout = 15000;
    /**
     * 对接签名秘钥
     */
    private String key;
    /**
     * 对接安全证书
     */
    private String certFile;
    /**
     * 报文
     */
    private final R msg;

    /**
     * 返回数据类型
     */
    private final Class<?> responseClass;

    public WxRequest(R msgInfo, Class<?> responseClass, String path) {
        this.msg = msgInfo;
        this.responseClass = responseClass;
        this.path = path;
    }

    public WxRequest<R> setNeedCert(boolean needCert) {
        this.needCert = needCert;
        return this;
    }

    public WxRequest<R> setCheckSign(boolean checkSign) {
        this.checkSign = checkSign;
        return this;
    }

    public WxRequest<R> setServerDomain(String serverDomain) {
        this.serverDomain = serverDomain;
        return this;
    }

    public WxRequest<R> setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public WxRequest<R> setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public WxRequest<R> setKey(String key) {
        this.key = key;
        return this;
    }

    public WxRequest<R> setCertFile(String certFile) {
        this.certFile = certFile;
        return this;
    }
}
