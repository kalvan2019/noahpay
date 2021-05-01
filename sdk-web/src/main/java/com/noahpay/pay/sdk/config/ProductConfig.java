package com.noahpay.pay.sdk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付请求
 *
 * @author chenliang
 */
@Component
@ConfigurationProperties(prefix = "product")
@Getter
@Setter
public class ProductConfig {
    /**
     * 显示公司名
     */
    private String company;
    /**
     * 浏览器图标favicon
     */
    private String favicon;
    /**
     * logo base64
     */
    private String logoData;
    /**
     * 首页链接
     */
    private String homeUrl;
    /**
     * 客服电话
     */
    private String telephone;

}