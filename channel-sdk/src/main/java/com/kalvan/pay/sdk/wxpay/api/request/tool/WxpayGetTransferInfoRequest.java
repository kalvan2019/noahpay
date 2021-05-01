package com.kalvan.pay.sdk.wxpay.api.request.tool;

import com.kalvan.pay.sdk.wxpay.api.WxpayConstants;
import com.kalvan.pay.sdk.wxpay.api.WxpayRequest;
import com.kalvan.pay.sdk.wxpay.api.response.tool.WxpayGetTransferInfoResponse;
import com.kalvan.pay.sdk.wxpay.api.util.WxpayHashMap;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * wxpay gettransferinfo request
 */
@Getter
@Setter
public class WxpayGetTransferInfoRequest implements WxpayRequest<WxpayGetTransferInfoResponse> {
    /**
     * 查询企业付款接口
     */
    private String bizContent;

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
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
        txtParams.put(WxpayConstants.BIZ_CONTENT, this.bizContent);
        return txtParams;
    }

    @Override
    public Class<WxpayGetTransferInfoResponse> getResponseClass() {
        return WxpayGetTransferInfoResponse.class;
    }

    @Override
    public boolean isNeedCert() {
        return true;
    }

    @Override
    public String serverDomain() {
        return WxpayConstants.DOMAIN_API_MCH;
    }

    @Override
    public boolean isCheckSign() {
        return false;
    }

}
