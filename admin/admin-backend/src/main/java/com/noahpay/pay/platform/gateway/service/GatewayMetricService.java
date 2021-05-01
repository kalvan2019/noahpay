package com.noahpay.pay.platform.gateway.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.platform.mapper.GatewayMetricMapper;
import com.noahpay.pay.commons.db.platform.model.GatewayMetric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 网关统计Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("gatewayMetricService")
public class GatewayMetricService extends BaseAuditService<GatewayMetric> {
    @Resource
    GatewayMetricMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "reportDate", false, false);
    }

}
