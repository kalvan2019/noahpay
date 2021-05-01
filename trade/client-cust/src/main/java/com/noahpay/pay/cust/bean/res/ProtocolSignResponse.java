package com.noahpay.pay.cust.bean.res;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenliang
 */
@Getter
@Setter
public class ProtocolSignResponse implements Serializable {
    /**
     * 平台协议号
     */
    private Long protocolNo;
    /**
     * 协议状态
     */
    private Integer protocolState;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 前端协议签约返回页面代码
     */
    private String htmlCode;
    /**
     * 银行卡号
     */
    private String bankAccountNo;
}
