package com.noahpay.pay.bean;

/**
 * api数据交互模型
 *
 * @author chenliang
 */
public class ApiModel {
    /**
     * AES key加密数据
     */
    public String data;
    /**
     * appId
     */
    public String appId;
    /**
     * 公钥加密过的AES key
     */
    public String key;
    /**
     * 私钥签名
     */
    public String sign;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
