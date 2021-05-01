package com.noahpay.pay.payment.settle.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.settle.mapper.ChannelCheckRecordMapper;
import com.noahpay.pay.commons.db.settle.model.ChannelCheckRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 对账主表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelCheckRecordService")
public class ChannelCheckRecordService extends BaseAuditService<ChannelCheckRecord> {
    @Resource
    ChannelCheckRecordMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

}
