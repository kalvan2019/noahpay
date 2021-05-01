package com.noahpay.pay.route.bean.res;

import com.noahpay.pay.route.bean.model.BankInfo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 路由返回对象
 *
 * @author chenliang
 */
@Setter
@Getter
public class RouteResponse implements java.io.Serializable {
    /**
     * 通道号
     */
    @NotNull
    private Integer channelNo;
    /**
     * 通道商户号
     */
    @NotBlank
    private String channelMerchantNo;
    /**
     * 通道商户名
     */
    private String channelMerchantName;
    /**
     * 通道子商户号
     */
    private String channelSubMerchantNo;
    /**
     * 通道子商户名
     */
    private String channelSubMerchantName;
    /**
     * 业务代码
     */
    private Integer mcc;
    /**
     * 地区码
     */
    private Integer city;
    /**
     * 优先级
     */
    private Integer priority;
    /**
     * 权重因子
     */
    private Integer weight;

    /**
     * 支持银行列表
     * 收银台路由返回
     */
    private List<BankInfo> bankInfos;
}
