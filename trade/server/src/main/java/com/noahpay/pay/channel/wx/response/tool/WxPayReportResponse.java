package com.noahpay.pay.channel.wx.response.tool;

import com.noahpay.pay.channel.wx.response.WxPayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay report response
 */
@Getter
@Setter
public class WxPayReportResponse extends WxPayResponse {

    private static final long serialVersionUID = 942049939513104128L;

    /*------------ 以下字段在return_code为SUCCESS的时候有返回  ------------*/

    /**
     * 业务结果，SUCCESS/FAIL
     */
    private String resultCode;
}
