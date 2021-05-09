package com.noahpay.pay.trade.bean.req;

import com.noahpay.pay.trade.bean.model.PayerInfo;
import com.noahpay.pay.trade.constant.PayTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 交易参数
 *
 * @author chenliang
 */
@Getter
@Setter
public class UnifiedOrderRequest extends OrderRequest implements java.io.Serializable {
    /**
     * 支付方式
     *
     * @see PayTypeEnum
     */
    @NotNull(message = "支付方式不能为空")
    private String payType;
    /**
     * 商品ID
     * payType=WX_NATIVE时，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
     */
    private String productId;
    /**
     * 付款方信息
     */
    @Valid
    private PayerInfo payerInfo;
}
