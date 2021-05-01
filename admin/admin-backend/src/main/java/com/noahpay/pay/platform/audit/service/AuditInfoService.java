package com.noahpay.pay.platform.audit.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.kalvan.client.context.RequestContextHolder;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminContextCacheKey;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.AuditState;
import com.noahpay.pay.commons.db.platform.mapper.AuditInfoMapper;
import com.noahpay.pay.commons.db.platform.model.AuditInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 审核信息Service实现类
 *
 * @author kalvan.tools
 */
@Slf4j
@Service("auditInfoService")
public class AuditInfoService extends BaseAuditService<AuditInfo> implements ApplicationContextAware {
    @Resource
    AuditInfoMapper mapper;

    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    public int auditing(Long[] ids, boolean pass) {
        String auditUser = String.valueOf(RequestContextHolder.getContext().get(AdminContextCacheKey.USER_NAME));
        int row = 0;
        for (Long id : ids) {
            AuditInfo auditInfo = auditInfoMapper.selectByUk(id);
            if (AuditState.WAIT.code != auditInfo.getAuditState()) {
                throw new BizException(AdminReturnCode.DATA_STATE_ERROR);
            }
            if (auditInfo.getApplicant().equals(auditUser)) {
                throw new BizException(AdminReturnCode.DATA_SELF_AUDIT);
            }
            if (pass) {
                try {
                    BaseAuditService service = (BaseAuditService) applicationContext.getBean(StrUtil.toCamelCase(auditInfo.getDataType()) + "Service");
                    ResolvableType resolvableType = ResolvableType.forClass(service.getClass()).getSuperType();
                    Type types = resolvableType.resolve().getGenericSuperclass();
                    Type[] genericType = ((ParameterizedType) types).getActualTypeArguments();
                    if (genericType.length == 0) {
                        throw new BizException(AdminReturnCode.REQUEST_SERVICE_NOT_SUPPORT.formatMessage(auditInfo.getDataType()));
                    }
                    Type type = genericType[0];
                    Object oldObject = null;
                    Object newObject = null;
                    String dataOld = auditInfo.getDataOld();
                    String dataNew = auditInfo.getDataNew();
                    if (!"{}".equals(dataOld)) {
                        oldObject = JSON.parseObject(dataOld, type);
                    }
                    if (!"{}".equals(dataNew)) {
                        newObject = JSON.parseObject(dataNew, type);
                    }
                    service.auditingUpdate(oldObject, newObject);
                } catch (BeansException e) {
                    throw new BizException(AdminReturnCode.REQUEST_SERVICE_NOT_SUPPORT.formatMessage(auditInfo.getDataType()));
                }
            }
            auditInfo.setAuditUser(auditUser);
            auditInfo.setAuditState(pass ? AuditState.PASS.code : AuditState.REFUSE.code);
            row = row + auditInfoMapper.updateByUkSelective(auditInfo);
        }
        return row;
    }
}
