package com.noahpay.pay.channel.wx.response.miniprogram;

import com.noahpay.pay.channel.wx.response.WxPayAuthResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay miniprogram auth jscode2session response
 *
 * @version 1.3.0
 */
@Getter
@Setter
public class WxPayAuthJsCodeToSessionResponse extends WxPayAuthResponse {

    private static final long serialVersionUID = 942049939513104121L;

    /**
     * 用户唯一标识
     */
    private String openid;
    /**
     * 会话密钥
     */
    private String sessionKey;
    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
     */
    private String unionid;
}
