package com.noahpay.pay.config;

/**
 * 公私钥生成
 *
 * @author chenliang
 */
public class AppCertInfo {
    /**
     * 接入方私钥
     * 接入方用来进行数据签名
     * 接入方用来进行数据解密
     */
    private String privateKey;
    /**
     * 接入方公钥,需要上传到平台
     * 平台用来进行数据加密
     */
    private String publicKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
