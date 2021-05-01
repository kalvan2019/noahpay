package com.noahpay.pay.platform.permission.service;

import com.kalvan.admin.dict.CacheUtil;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.DictType;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.DictMapper;
import com.noahpay.pay.commons.db.platform.model.Dict;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.web.feign.client.res.DictResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

/**
 * 数据字典配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("appService")
public class AppService extends BaseAuditService<Dict> {
    @Resource
    DictMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        params.put("dictType", DictType.SYSTEM_CODE);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(Dict dict) {
        Dict dictQuery = new Dict();
        dictQuery.setDictType(DictType.SYSTEM_CODE);
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
            dict.setSystemCode("*");
        }
        dict.setDictType(DictType.SYSTEM_CODE);
        return this.addDataAudit(dict);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(Dict dict) {
        Dict dictDb = mapper.selectByUk(dict.getId());
        if (dictDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        Dict dictNew = new Dict();
        dictNew.setId(dict.getId());
        dictNew.setDictKey(dict.getDictKey());
        dictNew.setDictValue(dict.getDictValue());
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
}
