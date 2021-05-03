package com.noahpay.pay.channel.wx.util;

import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import com.noahpay.pay.channel.wx.enums.WxpaySignType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 签名工具类
 */
@Getter
@Setter
public class WxpaySignature {

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
     *
     * @param data Map类型数据
     * @param key  API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
        return isSignatureValid(data, key, WxpaySignType.MD5.name());
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data     Map类型数据
     * @param key      API密钥
     * @param signType 签名方式
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key, String signType) throws Exception {
        if (!data.containsKey(WxPayConstants.SIGN)) {
            return false;
        }
        String sign = data.get(WxPayConstants.SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }

    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key  API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        return generateSignature(data, key, WxpaySignType.MD5.name());
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data     待签名数据
     * @param key      API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map data, String key, String signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WxPayConstants.SIGN)) {
                continue;
            }
            // 参数值为空，则不参与签名
            if (data.get(k) != null && String.valueOf(data.get(k)).trim().length() > 0) {
                sb.append(k).append("=").append(String.valueOf(data.get(k)).trim()).append("&");
            }
        }
        sb.append("key=").append(key);
        if (WxpaySignType.MD5.name().equals(signType)) {
            return WxpayEncrypt.MD5(sb.toString()).toUpperCase();
        } else if (WxpaySignType.HMACSHA256.name().equals(signType)) {
            return WxpayEncrypt.HMACSHA256(sb.toString(), key);
        } else {
            return WxpayEncrypt.MD5(sb.toString()).toUpperCase();
        }
    }

    /**
     * 生成 jsapi 签名
     *
     * @param data Map类型数据
     * @return jsapi签名
     */
    public static String generateJsapiSignature(Map<String, Object> data) {
        return DigestUtils.sha1Hex(WxpayUtils.getSortedParamsObject(data));
    }

}
