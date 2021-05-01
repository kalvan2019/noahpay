package com.kalvan.pay.sdk.wxpay.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * 微信授权基础响应信息
 */
@Getter
@Setter
public class WxpayAuthResponse implements Serializable {

    private static final long serialVersionUID = 2502494066966596561L;

    /**
     * 返回错误码，0为成功，其他值为错误码。
     */
    private String errcode;
    /**
     * 返回错误信息
     */
    private String errmsg;
    /**
     * 请求参数
     */
    private Map<String, String> params;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * 是否请求成功
     *
     * @return true/false
     */
    public boolean isSuccess() {
        return (this.errcode != null ? "0".equals(this.errcode) : true)
                && (this.errmsg != null ? WxpayConstants.OK.equals(this.errmsg) : true);
    }

}
