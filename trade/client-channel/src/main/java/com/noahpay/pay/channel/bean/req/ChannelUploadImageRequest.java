package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelUploadImageRequest extends ChannelBaseRequest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private byte[] imageArrays;
    private String imageFileName;
}
