package com.noahpay.pay.cust.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 客户交易检查
 *
 * @author chenliang
 */
@Getter
@Setter
public class SubMerchantCheckProtocolRequest implements Serializable {
    /**
     * 商户号
     */
    @NotNull(message = "商户号不可以为空")
    private Long merchantNo;
    /**
     * 子商户号
     */
    @NotNull(message = "子商户号不可以为空")
    private Long subMerchantNo;
    /**
     * 支付类型
     */
    @NotNull(message = "支付类型不允许为空")
    private String payType;
    /**
     * 交易金额
     */
    private Long amount;
    /**
     * 银行卡号
     */
    private String bankAccountNo;
    /**
     * 通道编号
     */
    private Integer channelNo;
    /**
     * 商户协议号
     */
    private String merchantProtocolNo;
}
