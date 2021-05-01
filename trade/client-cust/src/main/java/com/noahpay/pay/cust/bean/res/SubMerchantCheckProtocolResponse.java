package com.noahpay.pay.cust.bean.res;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenliang
 */
@Getter
@Setter
public class SubMerchantCheckProtocolResponse implements Serializable {
    /**
     * 银行卡预留手机号
     */
    private String mobile;
    /**
     * 银行协议号
     */
    private String bankProtocolNo;
    /**
     * 身份证号
     */
    private String certificateNo;
    /**
     * 最大金额限额
     */
    private Long limitMaxAmount;
    /**
     * 通道号
     */
    private Integer channelNo;
    /**
     * 通道商户号
     */
    private String channelMerchantNo;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 签约状态
     */
    private Integer state;
    /**
     * 扩展字段
     */
    private String extData;
}
