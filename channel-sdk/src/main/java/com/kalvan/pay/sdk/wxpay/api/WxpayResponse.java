package com.kalvan.pay.sdk.wxpay.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * 微信支付基础响应信息
 */
@Getter
@Setter
public class WxpayResponse implements Serializable {

    private static final long serialVersionUID = 6417856739210243713L;

    /**
     * 返回状态码  SUCCESS/FAIL <br/>
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断。
     */
    private String returnCode;
    /**
     * 返回信息 <br/>
     * 当return_code为FAIL时返回信息为错误原因 ，例如签名失败，参数格式校验错误。
     */
    private String returnMsg;
    /**
     * 请求参数
     */
    private Map<String, String> params;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * 请求是否成功
     *
     * @return true/false
     */
    public boolean isSuccess() {
        return WxpayConstants.SUCCESS.equals(this.returnCode)
                && (this.returnMsg == null || WxpayConstants.OK.equals(this.returnMsg));
    }

}
