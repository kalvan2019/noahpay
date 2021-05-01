package com.noahpay.pay.cust.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 通知商户签约结果
 *
 * @author chenliang
 */
@Getter
@Setter
public class NotifyMerchantProtocolSignRequest implements Serializable {
    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    private Long merchantNo;
    /**
     * 客户号
     */
    @NotNull(message = "客户号不能为空")
    private Long custNo;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 发往通道流水号
     */
    @NotBlank(message = "发往通道流水号不能为空")
    private String channelSendSn;
    /**
     * 银行协议号
     */
    private String bankProtocolNo;
    /**
     * 通道编号
     */
    @NotNull(message = "通道编号不能为空")
    private Integer channelNo;
    /**
     * 签约卡号
     */
    private String cardNo;
    /**
     * 脱敏后的签约卡号*号占位
     */
    private String cardNoSensitive;
    /**
     * 签约状态
     */
    @NotNull(message = "签约状态不能为空")
    private Integer state;
    /**
     * 状态说明
     */
    private String stateDesc;
}
