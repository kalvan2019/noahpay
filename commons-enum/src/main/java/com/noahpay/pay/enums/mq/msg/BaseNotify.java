package com.noahpay.pay.enums.mq.msg;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Getter
@Setter
public class BaseNotify {
    /**
     * 通知地址
     */
    String notifyUrl;
    /**
     * 商户号
     */
    Long merchantNo;
    /**
     * 子商户号
     */
    private Long subMerchantNo;
}
