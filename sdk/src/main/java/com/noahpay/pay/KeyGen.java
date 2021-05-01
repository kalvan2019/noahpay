package com.noahpay.pay;

import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.log.Log;

/**
 * 密钥生成
 *
 * @author chenliang
 */
public class KeyGen {
    private static final Log log = Log.get(KeyGen.class);

    public static void main(String[] args) {
        RSA rsa = new RSA();
        log.info("新生成私钥:{}", rsa.getPrivateKeyBase64());
        log.info("新生成公钥:{}", rsa.getPublicKeyBase64());
    }
}
