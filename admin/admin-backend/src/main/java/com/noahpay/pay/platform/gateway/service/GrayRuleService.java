package com.noahpay.pay.platform.gateway.service;

import com.kalvan.client.exception.BizException;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.GrayRuleMapper;
import com.noahpay.pay.commons.db.platform.model.GrayRule;
import com.noahpay.pay.enums.cache.CacheTypeEnum;
import com.kalvan.web.event.CacheRefreshEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 灰度规则配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("grayRuleService")
public class GrayRuleService extends BaseAuditService<GrayRule> {
    @Resource
    GrayRuleMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(GrayRule grayRule) {
        GrayRule grayRuleQuery = new GrayRule();
        grayRuleQuery.setRuleName(grayRule.getRuleName());
        if (mapper.selectCount(grayRuleQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(grayRule);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(GrayRule grayRule) {
        //TODO 修改数据检查
        GrayRule grayRuleDb = mapper.selectByUk(grayRule.getId());
        if (grayRuleDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        return this.editDataAudit(grayRuleDb, grayRule);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            GrayRule grayRuleDb = mapper.selectByUk(id);
            if (grayRuleDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(grayRuleDb);
        }
        return resultNum;
    }

    @Override
    public void auditingNotify(GrayRule oldObject, GrayRule newObject) {
        String ruleName;
        if (oldObject != null) {
            ruleName = oldObject.getRuleName();
        } else {
            ruleName = newObject.getRuleName();
        }
        updateCache(ruleName);
    }
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ServiceMatcher busServiceMatcher;

    public void updateCache(String ruleName) {
        CacheRefreshEvent event = new CacheRefreshEvent(this, busServiceMatcher.getServiceId(), null);
        event.setCacheType(CacheTypeEnum.GRAY_RULE);
        event.setCacheKey(ruleName);
        applicationEventPublisher.publishEvent(event);
    }
}
