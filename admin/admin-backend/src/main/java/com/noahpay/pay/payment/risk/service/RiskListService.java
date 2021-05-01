package com.noahpay.pay.payment.risk.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.db.risk.mapper.RiskListMapper;
import com.noahpay.pay.commons.db.risk.model.RiskList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 风险事件记录Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("riskListService")
public class RiskListService extends BaseAuditService<RiskList> {
    @Resource
    RiskListMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

}
