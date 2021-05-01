package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelCheckRequest extends ChannelBaseRequest {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "对账日期不能为空")
    private Integer checkDate;
    /**
     * 本地文件下载目录
     */
    private String localFileDir;
}
