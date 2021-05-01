package com.kalvan.pay.sdk.wxpay.api.request.pay;

import com.kalvan.pay.sdk.wxpay.api.WxpayConstants;
import com.kalvan.pay.sdk.wxpay.api.WxpayRequest;
import com.kalvan.pay.sdk.wxpay.api.response.pay.WxpayRefundNotifyResponse;
import com.kalvan.pay.sdk.wxpay.api.util.WxpayHashMap;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * wxpay refund notify request
 */
@Getter
@Setter
public class WxpayRefundNotifyRequest implements WxpayRequest<WxpayRefundNotifyResponse> {
    /**
     * 退款结果通知数据
     */
    private String notifyData;

    private boolean isCheckSign = false;

    public String getNotifyData() {
        return notifyData;
    }

    public void setNotifyData(String notifyData) {
        this.notifyData = notifyData;
    }

    @Override
    public boolean isCheckSign() {
        return isCheckSign;
    }

    public void setCheckSign(boolean isCheckSign) {
        this.isCheckSign = isCheckSign;
    }

    @Override
    public String serverDomain() {
        return null;
    }

    @Override
    public boolean isNeedCert() {
        return false;
    }

    @Override
    public String getNotifyUrl() {
        return null;
    }

    @Override
    public void setNotifyUrl(String notifyUrl) {
    }

    @Override
    public Map<String, String> getTextParams() {
        WxpayHashMap txtParams = new WxpayHashMap();
        txtParams.put(WxpayConstants.NOTIFY_DATA, this.notifyData);
        return txtParams;
    }

    @Override
    public Class<WxpayRefundNotifyResponse> getResponseClass() {
        return WxpayRefundNotifyResponse.class;
    }

}
