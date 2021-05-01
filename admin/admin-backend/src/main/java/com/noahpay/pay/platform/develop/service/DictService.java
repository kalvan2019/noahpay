package com.noahpay.pay.platform.develop.service;

import com.kalvan.admin.dict.CacheUtil;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.system.SystemUtil;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.DictMapper;
import com.noahpay.pay.commons.db.platform.model.Dict;
import com.noahpay.pay.enums.cache.CacheTypeEnum;
import com.kalvan.web.event.CacheRefreshEvent;
import com.kalvan.web.feign.client.res.DictResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

/**
 * 数据字典配置Service实现类
 *
 * @author kalvan
 * @date 2020-08-23 15:07:36
 */
@Slf4j
@Service
public class DictService extends BaseAuditService<Dict> {
    @Resource
    DictMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(Dict dict) {
        // TODO 填充数据,选择字段进行检查，更新人，更新时间，查重
        Dict dictQuery = new Dict();
        dictQuery.setDictType(dict.getDictType());
        dictQuery.setDictKey(dict.getDictKey());
        dictQuery.setDictValue(dict.getDictValue());
        if (mapper.selectCount(dictQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT.formatMessage(dict.getDictKey()));
        }
        Collection<DictResponse> dictList = CacheUtil.cache.getDictMap(null, dict.getDictType()).values();
        if (!dictList.isEmpty()) {
            dict.setDictName(dictList.stream().findFirst().get().getDictName());
        }
        if (StringUtils.isBlank(dict.getSystemCode())) {
            dict.setSystemCode(SystemUtil.constants.getName());
        }
        return this.addDataAudit(dict);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(Dict dict) {
        Dict dictDb = mapper.selectByUk(dict.getId());
        if (dictDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 更新指定字段，更新人
        Dict dictNew = new Dict();
        dictNew.setId(dict.getId());
        dictNew.setDictType(dict.getDictType());
        dictNew.setDictName(dict.getDictName());
        dictNew.setDictKey(dict.getDictKey());
        dictNew.setDictValue(dict.getDictValue());
        dictNew.setDictIcon(dict.getDictIcon());
        dictNew.setSelectEnable(dict.getSelectEnable());
        dictNew.setPriority(dict.getPriority());
        return this.editDataAudit(dictDb, dictNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            Dict dictDb = mapper.selectByUk(id);
            if (dictDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(dictDb);
        }
        return resultNum;
    }

    @Override
    public void auditingNotify(Dict oldObject, Dict newObject) {
        String dictType;
        if (oldObject != null) {
            dictType = oldObject.getDictType();
        } else {
            dictType = newObject.getDictType();
        }
        updateCache(dictType);
    }

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ServiceMatcher busServiceMatcher;

    public void updateCache(String dictType) {
        CacheRefreshEvent event = new CacheRefreshEvent(this, busServiceMatcher.getServiceId(), null);
        event.setCacheType(CacheTypeEnum.DICT);
        event.setCacheKey(dictType);
        applicationEventPublisher.publishEvent(event);
    }
}
