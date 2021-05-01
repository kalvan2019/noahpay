package com.kalvan.pay.sdk.wxpay.api.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 请求参数体
 */
@Getter
@Setter
public class RequestParametersHolder {

    /**
     * 申请参数
     */
    private WxpayHashMap applicationParams;

    public WxpayHashMap getApplicationParams() {
        return applicationParams;
    }

    public void setApplicationParams(WxpayHashMap applicationParams) {
        this.applicationParams = applicationParams;
    }

}
