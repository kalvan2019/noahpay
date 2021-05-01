package com.noahpay.pay.trade.config;

import cn.hutool.core.net.NetUtil;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.trade.util.TransIdUtils;
import com.noahpay.pay.trade.constant.TransReturnCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author chenliang
 */
@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "node")
public class NodeInfo {
    public static final String PROD = "prod";
    /**
     * 服务节点
     */
    List<String> ip;

    @Value("${spring.profiles.active}")
    String profile;

    @PostConstruct()
    public void init() {
        String localHost = NetUtil.getLocalhostStr();
        log.info("当前ip{}", localHost);
        int currentNode = ip.indexOf(localHost);
        log.info("当前节点{}", currentNode);
        if (currentNode == -1) {
            if (PROD.equals(profile)) {
                throw new BizException(TransReturnCode.PARAM_VALIDATE_ERROR.formatMessage("生成id失败,未配置服务节点信息"));
            } else {
                currentNode = 0;
            }
        }
        TransIdUtils.initNode(ip.size(), currentNode + 1);
    }
}