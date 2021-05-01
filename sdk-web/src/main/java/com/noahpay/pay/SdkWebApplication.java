package com.noahpay.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author chenliang
 */
@SpringBootApplication
public class SdkWebApplication extends SpringBootServletInitializer {
    static String newSdkUrl;

    @Value("${sdk.url:}")
    public void setSdkUrl(String sdkUrl) {
        newSdkUrl = sdkUrl;
    }

    public static void main(String[] args) {
        SpringApplication.run(SdkWebApplication.class, args);
        if (!"".equals(newSdkUrl)) {
            //默认配置在sdk文件中,可通过该配置匹配不同环境
            ApiServer.APP_SDK.getSdkInfo().setUrl(newSdkUrl);
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SdkWebApplication.class);
    }
}
