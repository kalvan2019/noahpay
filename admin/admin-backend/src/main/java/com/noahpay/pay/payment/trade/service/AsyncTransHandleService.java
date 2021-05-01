package com.noahpay.pay.payment.trade.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.trade.mapper.AsyncTransHandleMapper;
import com.noahpay.pay.commons.db.trade.model.AsyncTransHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 交易异步处理任务表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("asyncTransHandleService")
public class AsyncTransHandleService extends BaseAuditService<AsyncTransHandle> {
    @Resource
    AsyncTransHandleMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "dealTimes", false, false);
        convertBetweenParams(params, "updateTime", true, false);
    }

}
