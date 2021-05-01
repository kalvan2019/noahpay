package com.kalvan.pay.sdk.wxpay.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenliang
 */
@Getter
@Setter
public class SubMerchantInfo implements Serializable {
    /**
     * 子商户公众账号ID
     */
    private String sub_appid;
    /**
     * 子商户号
     */
    private String sub_mch_id;
    /**
     * 子商户秘钥
     */
    private String sub_key;
    /**
     * 子商户安全证书
     */
    private String sub_cert_file;
}
