package com.noahpay.pay.payment.cust.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.cust.mapper.MerchantConsumerProtocolMapper;
import com.noahpay.pay.commons.db.cust.model.MerchantConsumerProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商户用户银行协议Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("merchantConsumerProtocolService")
public class MerchantConsumerProtocolService extends BaseAuditService<MerchantConsumerProtocol> {
    @Resource
    MerchantConsumerProtocolMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

}
