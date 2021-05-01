package com.noahpay.pay.commons.web.interceptor;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import com.kalvan.admin.annotation.ParamsDecrypted;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 解密请求参数
 *
 * @author chenliang
 */
@Component
public class DecryptInterceptor extends HandlerInterceptorAdapter {
    /**
     * 偏移量,必须是16位字符串
     */
    @Value("${key.iv:16-Bytes--String}")
    String iv;
    /**
     * 私钥
     */
    @Value("${key.private:MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJPJc0lNmx3WHhC38iXWPbi7uhmxNsPUF9prF/QuDlbGC81Y1Cpb5CrZS1d2nGco6Nkw5gmiy4mqyeZ9UGmrJSwYNMdXNo+M4SbzYgm9IfjfmHs12L6zWOEinejkp8m7oNot74TSJM8yCWfp8B9nXSIvD1j56BK7xmNbsFCj+mSpAgMBAAECgYAiZnb+biEYPdn7vEWSRx9Q2+nCxzxfMwRmZB1MSlguHKVspCumvIMM8k2WS1a2sJAa1kYFoi5MmkOq8iqAnkTldxNOs+PmEsvByrhN0KjZpaJ9rYB4b89DNosFxWuOy6D4wK5OC+t3u2XR5QwVpXnf5CSJqk7fxaRU53SibkgJ8QJBAMntJxrK1KKyMnSr8RxVUbj2LpmoDnv0UCHdOTgNj8Nq3sQR9reDH9ndErspxgF/6vUFlVZBtnTipGt6Z5mJHL0CQQC7XNMUjhDBtaY8QWO1aZ7vykRrwbKCvw0fDk/HKW9l+1dug080IXKMOEJoFWUI5z6tZyfk0db0+lulfacTGARdAkB1U004XQflrIJylgEXRF+ih7AV6bbYwODafE6lygNxOSRMal+eyxpznOcoRqJc9rVPPrpBjxbznUiJhmqxpCBFAkA+4ZFqBZ/0Ey3ebSe0m+UenyjFbXWm0c3qjVgTBIIBnRAj4+fyHAOgscT/v6ZwDYQImGVYM2DMSTX23av0CYx1AkBd26N/nT0Pn3HsrXw3gHxQlK/XC3vgfCvH8BgFTxdV4+/LDdMtPSiKPXMsESzVTD7RhOb6FsIISTtK7OLijBOK}")
    String privateKey;
    /**
     * 公钥
     */
    @Value("${key.public:MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCTyXNJTZsd1h4Qt/Il1j24u7oZsTbD1Bfaaxf0Lg5WxgvNWNQqW+Qq2UtXdpxnKOjZMOYJosuJqsnmfVBpqyUsGDTHVzaPjOEm82IJvSH435h7Ndi+s1jhIp3o5KfJu6DaLe+E0iTPMgln6fAfZ10iLw9Y+egSu8ZjW7BQo/pkqQIDAQAB}")
    String publicKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ParamsDecrypted paramsDecrypted = null;
        if (handler instanceof HandlerMethod) {
            paramsDecrypted = ((HandlerMethod) handler).getMethod().getAnnotation(ParamsDecrypted.class);
        }
        //paramsDecrypted没标识注解默认为需要加解密
        if ((paramsDecrypted == null || paramsDecrypted.required())) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap.size() > 0) {
                for (Map.Entry<String, String[]> kv : parameterMap.entrySet()) {
                    //解密数据
                    String value = decryptParam(request.getHeader("key"), kv.getValue()[0]);
                    parameterMap.put(kv.getKey(), new String[]{value});
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    public String decryptParam(String key, String param) {
        try {
            //解密得到aes密钥
            key = new RSA(privateKey, publicKey).decryptStr(key, KeyType.PrivateKey);
            //解密得到明文
            return new AES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes()).decryptStr(param);
        } catch (Exception e) {
            return null;
        }
    }
}

