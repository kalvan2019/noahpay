package com.kalvan.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author chenliang
 */
@SpringBootApplication
public class ChannelSdkApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ChannelSdkApplication.class, args);
    }
}
