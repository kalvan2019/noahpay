package com.noahpay.pay.platform.gateway.service;

import com.alibaba.fastjson.JSON;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.GatewayAuthorizeMapper;
import com.noahpay.pay.commons.db.platform.model.GatewayAuthorize;
import com.noahpay.pay.enums.cache.CacheTypeEnum;
import com.noahpay.pay.enums.mq.msg.GatewayCache;
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
 * 接入方服务授权表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("gatewayAuthorizeService")
public class GatewayAuthorizeService extends BaseAuditService<GatewayAuthorize> {
    @Resource
    GatewayAuthorizeMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "maxAccess", false, false);
        convertBetweenParams(params, "times", false, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(GatewayAuthorize gatewayAuthorize) {
        GatewayAuthorize gatewayAuthorizeQuery = new GatewayAuthorize();
        gatewayAuthorizeQuery.setAppId(gatewayAuthorize.getAppId());
        gatewayAuthorizeQuery.setService(gatewayAuthorize.getService());
        if (mapper.selectCount(gatewayAuthorizeQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(gatewayAuthorize);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(GatewayAuthorize gatewayAuthorize) {
        //TODO 修改数据检查
        GatewayAuthorize gatewayAuthorizeDb = mapper.selectByUk(gatewayAuthorize.getId());
        if (gatewayAuthorizeDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        return this.editDataAudit(gatewayAuthorizeDb, gatewayAuthorize);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            GatewayAuthorize gatewayAuthorizeDb = mapper.selectByUk(id);
            if (gatewayAuthorizeDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(gatewayAuthorizeDb);
        }
        return resultNum;
    }

    @Override
    public void auditingNotify(GatewayAuthorize oldObject, GatewayAuthorize newObject) {
        String appId;
        String service;
        if (oldObject != null) {
            appId = oldObject.getAppId();
            service = oldObject.getService();
        } else {
            appId = newObject.getAppId();
            service = newObject.getService();
        }
        updateCache(appId, service);
    }

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ServiceMatcher busServiceMatcher;
    public void updateCache(String appId, String service) {
        GatewayCache gatewayCache = new GatewayCache();
        gatewayCache.setAppId(appId);
        gatewayCache.setService(service);

        CacheRefreshEvent event = new CacheRefreshEvent(this, busServiceMatcher.getServiceId(), null);
        event.setCacheType(CacheTypeEnum.GATEWAY_APP_SERVICE);
        event.setCacheKey(JSON.toJSONString(gatewayCache));
        applicationEventPublisher.publishEvent(event);
    }
}
