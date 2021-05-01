package com.noahpay.pay.platform.develop.service;

import com.kalvan.client.exception.BizException;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.MockServiceMapper;
import com.noahpay.pay.commons.db.platform.model.MockService;
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
 * mock服务配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("mockServiceService")
public class MockServiceService extends BaseAuditService<MockService> {
    @Resource
    MockServiceMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(MockService mockService) {
        return this.addDataAudit(mockService);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(MockService mockService) {
        MockService mockServiceDb = mapper.selectByUk(mockService.getId());
        if (mockServiceDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        MockService mockServiceNew = new MockService();
        mockServiceNew.setId(mockService.getId());
        mockServiceNew.setId(mockService.getId());
        mockServiceNew.setServiceId(mockService.getServiceId());
        mockServiceNew.setService(mockService.getService());
        mockServiceNew.setMockUrl(mockService.getMockUrl());
        mockServiceNew.setState(mockService.getState());
        return this.editDataAudit(mockServiceDb, mockServiceNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            MockService mockServiceDb = mapper.selectByUk(id);
            if (mockServiceDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(mockServiceDb);
        }
        return resultNum;
    }

    @Override
    public void auditingNotify(MockService oldObject, MockService newObject) {
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
        event.setCacheType(CacheTypeEnum.MOCK_URL);
        event.setCacheKey(service);
        applicationEventPublisher.publishEvent(event);
    }
}
