package com.noahpay.pay.platform.gateway.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.GatewayServiceMapper;
import com.noahpay.pay.commons.db.platform.model.GatewayService;
import com.noahpay.pay.enums.cache.CacheTypeEnum;
import com.kalvan.web.event.CacheRefreshEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 服务发布Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("gatewayServiceService")
public class GatewayServiceService extends BaseAuditService<GatewayService> {
    @Resource
    GatewayServiceMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "maxAccess", false, false);
        convertBetweenParams(params, "times", false, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(GatewayService gatewayService) {
        GatewayService gatewayServiceQuery = new GatewayService();
        gatewayServiceQuery.setService(gatewayService.getService());
        if (mapper.selectCount(gatewayServiceQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(gatewayService);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(GatewayService gatewayService) {
        GatewayService gatewayServiceDb = mapper.selectByUk(gatewayService.getId());
        if (gatewayServiceDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        return this.editDataAudit(gatewayServiceDb, gatewayService);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            GatewayService gatewayServiceDb = mapper.selectByUk(id);
            if (gatewayServiceDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(gatewayServiceDb);
        }
        return resultNum;
    }

    @Override
    public void auditingNotify(GatewayService oldObject, GatewayService newObject) {
        String service;
        if (oldObject != null) {
            service = oldObject.getService();
        } else {
            service = newObject.getService();
        }
        updateCache(service);
    }
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ServiceMatcher busServiceMatcher;

    public void updateCache(String service) {
        CacheRefreshEvent event = new CacheRefreshEvent(this, busServiceMatcher.getServiceId(), null);
        event.setCacheType(CacheTypeEnum.GATEWAY_SERVICE);
        event.setCacheKey(service);
        applicationEventPublisher.publishEvent(event);
    }

    @Cached(name = CacheTypeEnum.ALL_GATEWAY_SERVICE, cacheType = CacheType.LOCAL, expire = 600, localLimit = 1000)
    public List<GatewayService> getAllService() {
        return mapper.getAllService();
    }

    @CacheInvalidate(name = CacheTypeEnum.ALL_GATEWAY_SERVICE)
    public void deleteAllService() {
    }
}
