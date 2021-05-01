package com.kalvan.pay.sdk.wxpay.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 微信JS-SDK基础响应信息
 */
@Getter
@Setter
public class WxpayJsApiResponse implements Serializable {

    private static final long serialVersionUID = 2502494060966596561L;

    /**
     * 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
     */
    private Boolean debug = false;
    /**
     * 公众号的唯一标识
     */
    private String appId;
    /**
     * 生成签名的时间戳
     */
    private long timestamp;
    /**
     * 生成签名的随机串
     */
    private String nonceStr;
    /**
     * 签名
     */
    private String signature;
    /**
     * 需要使用的JS接口列表
     */
    private List<String> jsApiList;
    /**
     * 请求参数
     */
    private Map<String, Object> params;

    public Boolean isDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<String> getJsApiList() {
        return jsApiList;
    }

    public void setJsApiList(List<String> jsApiList) {
        this.jsApiList = jsApiList;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

}
