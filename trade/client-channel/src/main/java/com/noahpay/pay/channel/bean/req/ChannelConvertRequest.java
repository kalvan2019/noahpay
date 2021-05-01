/*
 * 文件名：ChannelConvertParam.java
 *
 * 修改人：Chen Liang
 * 修改时间：2016年1月26日
 * 修改内容：
 */
package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelConvertRequest extends ChannelBaseRequest implements
        java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * type与value维持一组关系
     */
    private String payType;
    /***
     * 通道证件类型
     */
    private Integer certificateType;
    /**
     * 通道行别
     */
    private String bankType;
    /**
     * 账户类型
     */
    private Integer bankAccountType;
    /**
     * 地区码
     */
    private Integer city;
    /**
     * 通道业务代码
     */
    private Integer mcc;
}
