package com.noahpay.pay.trade.bean.model;

import com.noahpay.pay.trade.constant.PayTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 付款人信息
 *
 * @author chenliang
 */
@Getter
@Setter
public class PayerInfo implements java.io.Serializable {
    /**
     * 用户标识（机构）
     */
    private String spOpenid;

    /**
     * 用户标识（特约商户）
     */
    private String subOpenid;
}
