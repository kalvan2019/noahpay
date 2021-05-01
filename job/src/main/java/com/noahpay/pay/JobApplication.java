package com.noahpay.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 定时任务
 *
 * @author chenliang
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class JobApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JobApplication.class);
    }
}