package com.noahpay.pay;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.kalvan.web.KalvanWebProperties;
import com.noahpay.pay.trade.event.MqOutput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author chenliang
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.noahpay.pay")
//@RemoteApplicationEventScan(basePackages= KalvanWebProperties.BASE_PACKAGES)
@ComponentScan(basePackages = {"com.kalvan", "com.noahpay.pay"})
@EnableAsync(proxyTargetClass = true)
@EnableBinding({MqOutput.class})
public class TradeApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TradeApplication.class);
    }
}
