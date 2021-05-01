package com.noahpay.pay.platform.gateway.service;

import com.kalvan.client.exception.BizException;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.GatewayRouteMapper;
import com.noahpay.pay.commons.db.platform.model.GatewayRoute;
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
 * 网关路由表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("gatewayRouteService")
public class GatewayRouteService extends BaseAuditService<GatewayRoute> {
    @Resource
    GatewayRouteMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(GatewayRoute gatewayRoute) {
        GatewayRoute gatewayRouteQuery = new GatewayRoute();
        gatewayRouteQuery.setRouteId(gatewayRoute.getRouteId());
        if (mapper.selectCount(gatewayRouteQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(gatewayRoute);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(GatewayRoute gatewayRoute) {
        //TODO 修改数据检查
        GatewayRoute gatewayRouteDb = mapper.selectByUk(gatewayRoute.getId());
        if (gatewayRouteDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        return this.editDataAudit(gatewayRouteDb, gatewayRoute);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            GatewayRoute gatewayRouteDb = mapper.selectByUk(id);
            if (gatewayRouteDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(gatewayRouteDb);
        }
        return resultNum;
    }

    @Override
    public void auditingNotify(GatewayRoute oldObject, GatewayRoute newObject) {
        String routeId;
        if (oldObject != null) {
            routeId = oldObject.getRouteId();
        } else {
            routeId = newObject.getRouteId();
        }
        updateCache(routeId);
    }
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ServiceMatcher busServiceMatcher;

    public void updateCache(String routeId) {
        CacheRefreshEvent event = new CacheRefreshEvent(this, busServiceMatcher.getServiceId(), null);
        event.setCacheType(CacheTypeEnum.GATEWAY_ROUTE);
        event.setCacheKey(routeId);
        applicationEventPublisher.publishEvent(event);
    }
}
