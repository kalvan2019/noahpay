package com.noahpay.pay.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.log.Log;
import com.noahpay.pay.bean.ApiModel;
import com.noahpay.pay.config.AppCertInfo;
import com.noahpay.pay.config.SdkInfo;

/**
 * sdk工具类
 *
 * @author chenliang
 */
public class AppSdk {
    private static final Log log = Log.get(AppSdk.class);

    /**
     * 加密&签名生成请求数据
     *
     * @param appId appId
     * @param data  数据明文
     * @return map
     */
    public ApiModel encryptAndSign(String appId, String data, Mode mode, SignAlgorithm signAlgorithm) {
        ApiModel encryptAndSign = new ApiModel();
        AppCertInfo appCertInfo = this.getSdkInfo().getAppCertInfoMap().get(appId);
        if (appCertInfo == null) {
            log.error("未配置证书");
            return null;
        }
        //aes随机密码加密明文
        String key = RandomUtil.randomNumbers(16);
        AES aes = new AES(mode, Padding.PKCS5Padding, key.getBytes());
        String encryptDataBase64 = aes.encryptBase64(data);
        //使用plat公钥加密 AES key base64
        RSA platRsa = new RSA(null, this.getSdkInfo().getPlatPublicKey());
        String encryptKeyBase64 = platRsa.encryptBase64(key, KeyType.PublicKey);
        //使用app私钥签名明文
        Sign appSign = new Sign(signAlgorithm, appCertInfo.getPrivateKey(), appCertInfo.getPublicKey());
        String signBase64 = Base64.encode(appSign.sign(data.getBytes()));
        encryptAndSign.setAppId(appId);
        encryptAndSign.setKey(encryptKeyBase64);
        encryptAndSign.setData(encryptDataBase64);
        encryptAndSign.setSign(signBase64);
        return encryptAndSign;
    }

    /**
     * 加载&签名
     *
     * @param appId appId
     * @param data  数据明文
     * @return map
     */
    public ApiModel encryptAndSign(String appId, String data) {
        return encryptAndSign(appId, data, Mode.ECB, SignAlgorithm.SHA1withRSA);
    }

    /**
     * 解密&验证签名
     *
     * @param encryptAndSign 加密内容
     * @return map
     */
    public ApiModel decryptAndVerify(ApiModel encryptAndSign, Mode mode, SignAlgorithm signAlgorithm) {
        String appId = encryptAndSign.getAppId();
        String encryptKeyBase64 = encryptAndSign.getKey();
        if (encryptKeyBase64 == null || "".equals(encryptKeyBase64.trim())) {
            //特殊情况下没有加密的返回报文
            return encryptAndSign;
        }
        String encryptDataBase64 = encryptAndSign.getData();
        String signBase64 = encryptAndSign.getSign();
        AppCertInfo appCertInfo = this.getSdkInfo().getAppCertInfoMap().get(appId);
        if (appCertInfo == null) {
            log.error("未配置证书");
            return null;
        }
        //使用app私钥解密 AES key
        RSA appRsa = new RSA(appCertInfo.getPrivateKey(), appCertInfo.getPublicKey());
        String key = appRsa.decryptStr(encryptKeyBase64, KeyType.PrivateKey);
        //aes解密密文
        AES aes = new AES(mode, Padding.PKCS5Padding, key.getBytes());
        String plaintextData = aes.decryptStr(encryptDataBase64);
        //使用plat公钥验证签名
        Sign platSign = new Sign(signAlgorithm, null, this.getSdkInfo().getPlatPublicKey());
        boolean verify = platSign.verify(plaintextData.getBytes(), Base64.decode(signBase64));
        if (!verify) {
            log.error("验证签名失败");
            return null;
        }
        //明文数据
        encryptAndSign.setData(plaintextData);
        return encryptAndSign;
    }

    /**
     * 解密&验证签名
     * aes 模式 Mode.ECB
     * rsa 签名算法 SignAlgorithm.SHA1withRSA
     *
     * @param encryptAndSign decryptAndVerify
     * @return map
     */
    public ApiModel decryptAndVerify(ApiModel encryptAndSign) {
        return decryptAndVerify(encryptAndSign, Mode.ECB, SignAlgorithm.SHA1withRSA);
    }

    public SdkInfo getSdkInfo() {
        return SdkInfo.getInstance();
    }
}
