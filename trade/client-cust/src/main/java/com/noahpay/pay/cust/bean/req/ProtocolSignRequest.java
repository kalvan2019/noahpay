package com.noahpay.pay.cust.bean.req;

import com.noahpay.pay.cust.bean.model.BankCardInfo;
import com.noahpay.pay.cust.bean.model.CertificateInfo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 客户绑卡
 *
 * @author chenliang
 */
@Getter
@Setter
public class ProtocolSignRequest implements Serializable {
    /**
     * 订单号
     */
    @Size(min = 1, max = 32, message = "订单号长度需在1-32位")
    @NotBlank(message = "订单号不能为空")
    private String orderId;
    /**
     * 银行卡信息
     */
    @Valid
    private BankCardInfo bankCardInfo;
    /**
     * 证件信息
     */
    @Valid
    private CertificateInfo certificateInfo;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 协议支付限额
     */
    @NotNull(message = "协议支付限额不能为空")
    private Integer limitMaxAmount;
    /**
     * 协议生效日期
     * yyyyMMdd
     */
    @NotNull(message = "协议生效日期能为空")
    private Integer effectiveTime;
    /**
     * 协议失效日期
     * yyyyMMdd
     */
    @NotNull(message = "协议失效日期不能为空")
    private Integer expiryTime;
    /**
     * ip
     */
    private String ip;
    /**
     * 前端通知地址
     */
    private String pgUrl;
    /**
     * 后端通知地址
     */
    private String bgUrl;
}
