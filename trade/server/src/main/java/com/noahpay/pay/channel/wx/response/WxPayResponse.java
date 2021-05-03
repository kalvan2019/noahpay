package com.noahpay.pay.channel.wx.response;

import com.noahpay.pay.channel.wx.enums.WxPayConstants;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * 微信支付基础响应信息
 */
@Getter
@Setter
public class WxPayResponse implements Serializable {

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
    private Map<String, Object> params;

    /**
     * 请求是否成功
     *
     * @return true/false
     */
    public boolean isSuccess() {
        return WxPayConstants.SUCCESS.equals(this.returnCode)
                && (this.returnMsg == null || WxPayConstants.OK.equals(this.returnMsg));
    }

}
