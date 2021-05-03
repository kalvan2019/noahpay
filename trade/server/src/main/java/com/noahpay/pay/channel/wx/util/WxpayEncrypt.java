package com.noahpay.pay.channel.wx.util;

import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Arrays;

/**
 * 加密工具类
 *
 * @version 1.3.0
 */
@Getter
@Setter
public class WxpayEncrypt {

    /**
     * AES加密
     *
     * @param data 待处理数据
     * @param key  秘钥
     * @return AES加密结果
     * @throws WxPayApiException
     */
    public static String aesEncrypt(String data, String key) throws WxPayApiException {
        Security.addProvider(new BouncyCastleProvider());
        try {
            Cipher cipher = Cipher.getInstance(WxPayConstants.ALGORITHM_AES_MODE_PADDING, WxPayConstants.ALGORITHM_AES_PROVIDER);
            SecretKeySpec secretKey = new SecretKeySpec(MD5(key).toLowerCase().getBytes(), WxPayConstants.ENCRYPT_TYPE_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return base64Encrypt(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new WxPayApiException("AES encrypt fail：AES data = " + data, e);
        }
    }

    /**
     * AES解密
     *
     * @param data 待处理数据
     * @param key  秘钥
     * @return AES解密结果
     * @throws WxPayApiException
     */
    public static String aesDecrypt(String data, String key) throws WxPayApiException {
        Security.addProvider(new BouncyCastleProvider());
        try {
            Cipher cipher = Cipher.getInstance(WxPayConstants.ALGORITHM_AES_MODE_PADDING, WxPayConstants.ALGORITHM_AES_PROVIDER);
            SecretKeySpec secretKey = new SecretKeySpec(MD5(key).toLowerCase().getBytes(), WxPayConstants.ENCRYPT_TYPE_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(base64Decrypt(data)));
        } catch (Exception e) {
            throw new WxPayApiException("AES decrypt fail,AES data = " + data, e);
        }
    }

    /**
     * base64加密
     *
     * @param data 待处理数据
     * @return base64加密结果
     * @throws UnsupportedEncodingException
     */
    public static String base64Encrypt(byte[] data) throws UnsupportedEncodingException {
        return new String(Base64.encodeBase64(data), StandardCharsets.UTF_8);
    }

    /**
     * base64加密
     *
     * @param data 待处理数据
     * @return base64加密结果
     * @throws UnsupportedEncodingException
     */
    public static String base64EncryptString(String data) throws UnsupportedEncodingException {
        return new String(Base64.encodeBase64(data.getBytes()), StandardCharsets.UTF_8);
    }

    /**
     * base64解密
     *
     * @param data 待处理数据
     * @return base64解密结果
     */
    public static byte[] base64Decrypt(String data) {
        return Base64.decodeBase64(data);
    }

    /**
     * base64解密
     *
     * @param data 待处理数据
     * @return base64解密结果
     * @throws UnsupportedEncodingException
     */
    public static String base64DecryptString(String data) throws UnsupportedEncodingException {
        return new String(Base64.decodeBase64(data), StandardCharsets.UTF_8);
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance(WxPayConstants.ENCRYPT_TYPE_MD5);
        byte[] array = md.digest(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     *
     * @param data 待处理数据
     * @param key  密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance(WxPayConstants.ENCRYPT_TYPE_HMACSHA256);
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), WxPayConstants.ENCRYPT_TYPE_HMACSHA256);
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString().toUpperCase();
    }


    /**
     * 小程序用户数据解密 (获取用户信息或手机号信息)
     *
     * @param sessionKey    用户的 session-key
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     * @throws WxPayApiException
     */
    public static String userInfoDecrypt(String sessionKey, String encryptedData, String iv) throws WxPayApiException {
        byte[] keyByte = base64Decrypt(sessionKey);
        int base = 16;
        try {
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(WxPayConstants.ALGORITHM_AES_MODE_PADDING2, WxPayConstants.ALGORITHM_AES_PROVIDER);
            SecretKeySpec spec = new SecretKeySpec(keyByte, WxPayConstants.ENCRYPT_TYPE_AES);
            AlgorithmParameters parameters = AlgorithmParameters.getInstance(WxPayConstants.ENCRYPT_TYPE_AES);
            parameters.init(new IvParameterSpec(base64Decrypt(iv)));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(base64Decrypt(encryptedData));
            return new String(resultByte, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new WxPayApiException("miniProgram userInfo decrypt fail,AES data = " + encryptedData, e);
        }
    }

}
