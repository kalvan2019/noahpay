package com.noahpay.pay.platform.gateway.service;

import com.kalvan.client.exception.BizException;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.GrayServiceMapper;
import com.noahpay.pay.commons.db.platform.model.GrayService;
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
 * 灰度服务配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("grayServiceService")
public class GrayServiceService extends BaseAuditService<GrayService> {
    @Resource
    GrayServiceMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(GrayService grayService) {
        GrayService grayServiceQuery = new GrayService();
        grayServiceQuery.setServiceId(grayService.getServiceId());
        grayServiceQuery.setRuleName(grayService.getRuleName());
        if (mapper.selectCount(grayServiceQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(grayService);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(GrayService grayService) {
        //TODO 修改数据检查
        GrayService grayServiceDb = mapper.selectByUk(grayService.getId());
        if (grayServiceDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        return this.editDataAudit(grayServiceDb, grayService);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            GrayService grayServiceDb = mapper.selectByUk(id);
            if (grayServiceDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(grayServiceDb);
        }
        return resultNum;
    }

    @Override
    public void auditingNotify(GrayService oldObject, GrayService newObject) {
        String serviceId;
        if (oldObject != null) {
            serviceId = oldObject.getServiceId();
        } else {
            serviceId = newObject.getServiceId();
        }
        updateCache(serviceId);
    }
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ServiceMatcher busServiceMatcher;

    public void updateCache(String serviceId) {
        CacheRefreshEvent event = new CacheRefreshEvent(this, busServiceMatcher.getServiceId(), null);
        event.setCacheType(CacheTypeEnum.GATEWAY_GRAY);
        event.setCacheKey(serviceId);
        applicationEventPublisher.publishEvent(event);
    }

}
