package com.noahpay.pay.trade.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 交易参数
 *
 * @author chenliang
 */
@Getter
@Setter
public class MicroPayRequest extends OrderRequest implements java.io.Serializable {
    /**
     * 扫码支付授权码
     * 设备读取用户的付款条码或者二维码信息
     */
    private String authCode;
}
