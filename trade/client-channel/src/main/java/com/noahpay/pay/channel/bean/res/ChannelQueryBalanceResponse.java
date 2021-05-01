package com.noahpay.pay.channel.bean.res;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelQueryBalanceResponse implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Long availableBalance;
}
