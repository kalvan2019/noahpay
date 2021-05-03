package com.noahpay.pay.trade.bean.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 交易返回对象
 *
 * @author chenliang
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransResponse implements java.io.Serializable {
    /**
     * 商户号
     */
    private Long merchantNo;
    /**
     * 交易流水号
     */
    @NotBlank
    private String transId;
    /**
     * 预支付交易会话标识
     */
    private String prepayId;
    /**
     * 二维码链接
     * trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付
     */
    private String codeUrl;
    /**
     * h5支付链接
     * trade_type=MWEB时有返回
     */
    private String webUrl;
}
