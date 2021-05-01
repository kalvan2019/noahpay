package com.kalvan.pay.sdk.wxpay.dto.request;

import com.kalvan.pay.sdk.wxpay.api.rules.WxpayTradeType;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信付款码支付业务请求参数
 */
@Getter
@Setter
public class WxpayMicropay extends WxpayUnifiedorder {

    private static final long serialVersionUID = 3980243882020227536L;
    /**
     * 扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     * （注：用户付款码条形码规则：18位纯数字，以10、11、12、13、14、15开头）
     */
    private String auth_code;

    public WxpayMicropay() {
        super();
    }

    /**
     * 付款码（刷卡）支付
     *
     * @param body
     * @param out_trade_no
     * @param total_fee
     * @param spbill_create_ip
     * @param auth_code
     */
    public WxpayMicropay(String body, String out_trade_no, String total_fee,
                         String spbill_create_ip, String auth_code) {
        super(body, out_trade_no, total_fee, spbill_create_ip, WxpayTradeType.MICROPAY);
        this.auth_code = auth_code;
    }
}
