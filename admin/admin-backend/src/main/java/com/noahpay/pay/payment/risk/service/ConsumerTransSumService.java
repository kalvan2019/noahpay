package com.noahpay.pay.payment.risk.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.risk.mapper.ConsumerTransSumMapper;
import com.noahpay.pay.commons.db.risk.model.ConsumerTransSum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 客户交易额度累计Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("consumerTransSumService")
public class ConsumerTransSumService extends BaseAuditService<ConsumerTransSum> {
    @Resource
    ConsumerTransSumMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

}
