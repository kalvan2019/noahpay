package com.noahpay.pay.config;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.log.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 密钥管理
 *
 * @author chenliang
 */
public class SdkInfo {
    public static final String SDK_URL = "sdk.url";
    public static final String SDK_CERT_PLAT_PUBLIC_KEY = "sdk.cert.platPublicKey";
    private static final Log logger = Log.get(SdkInfo.class);
    private static SdkInfo sdkInfo = null;
    /**
     * api地址
     */
    private String url;
    /**
     * 平台公钥,由平台提供
     * 接入方用来进行数据验签
     * 接入方用来进行数据加密
     */
    private String platPublicKey;
    /**
     * 接入方密钥配置
     */
    private Map<String, AppCertInfo> appCertInfoMap;

    private SdkInfo() {
        init();
    }

    public static SdkInfo getInstance() {
        if (sdkInfo == null) {
            sdkInfo = new SdkInfo();
        }
        return sdkInfo;
    }

    public String getPlatPublicKey() {
        return platPublicKey;
    }

    public void setPlatPublicKey(String platPublicKey) {
        this.platPublicKey = platPublicKey;
    }

    public Map<String, AppCertInfo> getAppCertInfoMap() {
        return appCertInfoMap;
    }

    public void setAppCertInfoMap(Map<String, AppCertInfo> appCertInfoMap) {
        this.appCertInfoMap = appCertInfoMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private void init() {
        Properties props = new Properties();
        try {
            props.load(ResourceUtil.getStream("sdk.properties"));
            //加载平台对接地址
            Set<String> names = props.stringPropertyNames();
            if (!names.contains(SDK_URL)) {
                logger.error("配置文件缺少: {}", SDK_URL);
            }
            this.setUrl(props.getProperty(SDK_URL));
            //加载平台公钥
            if (!names.contains(SDK_CERT_PLAT_PUBLIC_KEY)) {
                logger.error("配置文件缺少: {}", SDK_CERT_PLAT_PUBLIC_KEY);
            }
            this.setPlatPublicKey(props.getProperty(SDK_CERT_PLAT_PUBLIC_KEY));
            //加载appId密钥
            Map<String, AppCertInfo> appCertInfo = new HashMap<>(1);
            for (String key : names) {
                if (key.startsWith("sdk.cert") && key.endsWith("privateKey")) {
                    String privateKey = props.getProperty(key);
                    String value = key.replace("privateKey", "publicKey");
                    String publicKey = props.getProperty(value);
                    AppCertInfo aci = new AppCertInfo();
                    aci.setPrivateKey(privateKey);
                    aci.setPublicKey(publicKey);
                    String appId = key.replaceFirst("sdk.cert.", "").replaceFirst(".privateKey", "");
                    appCertInfo.put(appId, aci);
                }
            }
            this.setAppCertInfoMap(appCertInfo);
        } catch (IOException e) {
            logger.error("loading sdk.properties exception :{}", e.getMessage());
        }
    }
}
