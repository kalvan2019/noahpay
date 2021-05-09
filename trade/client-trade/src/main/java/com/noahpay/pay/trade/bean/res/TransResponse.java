package com.noahpay.pay.trade.bean.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.noahpay.pay.trade.bean.model.Amount;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotBlank
    private Long merchantNo;
    /**
     * 交易流水号
     */
    @NotBlank
    private String transId;
    /**
     * 附加数据
     * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    @NotBlank
    private String attach;
    /**
     * 订单金额
     */
    @NotNull
    private Amount amount;
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
    /**
     * 通道返回附加数据
     * json格式
     */
    private String channelRecvExt;
}
