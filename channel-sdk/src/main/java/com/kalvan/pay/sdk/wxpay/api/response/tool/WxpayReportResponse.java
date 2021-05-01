package com.kalvan.pay.sdk.wxpay.api.response.tool;

import com.kalvan.pay.sdk.wxpay.api.WxpayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay report response
 */
@Getter
@Setter
public class WxpayReportResponse extends WxpayResponse {

    private static final long serialVersionUID = 942049939513104128L;

    /*------------ 以下字段在return_code为SUCCESS的时候有返回  ------------*/

    /**
     * 业务结果，SUCCESS/FAIL
     */
    private String resultCode;
}
