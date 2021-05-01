package com.noahpay.pay.platform.gateway.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.AppInfoMapper;
import com.noahpay.pay.commons.db.platform.model.AppInfo;
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
 * app信息Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("appInfoService")
public class AppInfoService extends BaseAuditService<AppInfo> {
    @Resource
    AppInfoMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(AppInfo appInfo) {
        AppInfo appInfoQuery = new AppInfo();
        appInfoQuery.setAppId(appInfo.getAppId());
        if (mapper.selectCount(appInfoQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(appInfo);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(AppInfo appInfo) {
        AppInfo appInfoDb = mapper.selectByUk(appInfo.getId());
        if (appInfoDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        return this.editDataAudit(appInfoDb, appInfo);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            AppInfo appInfoDb = mapper.selectByUk(id);
            if (appInfoDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(appInfoDb);
        }
        return resultNum;
    }

    @Override
    public void auditingNotify(AppInfo oldObject, AppInfo newObject) {
        String appId;
        if (oldObject != null) {
            appId = oldObject.getAppId();
        } else {
            appId = newObject.getAppId();
        }
        updateCache(appId);
    }

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ServiceMatcher busServiceMatcher;
    public void updateCache(String appId) {
        CacheRefreshEvent event = new CacheRefreshEvent(this, busServiceMatcher.getServiceId(), null);
        event.setCacheType(CacheTypeEnum.GATEWAY_APP);
        event.setCacheKey(appId);
        applicationEventPublisher.publishEvent(event);
        event.setCacheType(CacheTypeEnum.GATEWAY_RSA);
        applicationEventPublisher.publishEvent(event);
    }

    @Cached(name = CacheTypeEnum.ALL_APP, cacheType = CacheType.LOCAL, expire = 600, localLimit = 1000)
    public List<AppInfo> getAllApp() {
        return mapper.getAllApp();
    }

    @CacheInvalidate(name = CacheTypeEnum.ALL_APP)
    public void deleteAllApp() {
    }
}
