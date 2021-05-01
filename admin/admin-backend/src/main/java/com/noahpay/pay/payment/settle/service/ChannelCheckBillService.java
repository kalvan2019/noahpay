package com.noahpay.pay.payment.settle.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.settle.mapper.ChannelCheckBillMapper;
import com.noahpay.pay.commons.db.settle.model.ChannelCheckBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道对账明细Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelCheckBillService")
public class ChannelCheckBillService extends BaseAuditService<ChannelCheckBill> {
    @Resource
    ChannelCheckBillMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

}
