package com.noahpay.pay.platform.audit.service;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.kalvan.admin.log.LogType;
import com.kalvan.client.constant.CommonReturnCode;
import com.kalvan.client.context.RequestContextHolder;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminContextCacheKey;
import com.noahpay.pay.commons.constant.AuditState;
import com.noahpay.pay.commons.db.platform.mapper.AuditInfoMapper;
import com.noahpay.pay.commons.db.platform.model.AuditInfo;
import com.kalvan.db.mybatis.BaseService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Service实现类
 *
 * @author kalvan
 */
@Slf4j
public abstract class BaseAuditService<T> extends BaseService<T> {
    @Resource
    AuditInfoMapper auditInfoMapper;

    /**
     * 新增数据审核
     *
     * @param dataNew 新增数据
     * @return row
     */
    public int addDataAudit(T dataNew) {
        AuditInfo auditInfo = new AuditInfo();
        //model名字=LogGroup.定义 方便共用字典
        String service = dataNew.getClass().getSimpleName();
        service = service.replaceAll("([a-z])([A-Z])", "$1" + "_" + "$2").toLowerCase();
        auditInfo.setDataType(service);
        auditInfo.setDataNew(JSON.toJSONString(dataNew));
        auditInfo.setApplicant(String.valueOf(RequestContextHolder.getContext().get(AdminContextCacheKey.USER_NAME)));
        auditInfo.setAuditType(LogType.ADD.code);
        auditInfo.setAuditState(AuditState.WAIT.code);
        return auditInfoMapper.insertSelective(auditInfo);
    }

    /**
     * 更新数据审核
     *
     * @param dataOld 更新前数据
     * @param dataNew 更新后数据
     * @return row
     */
    public int editDataAudit(T dataOld, T dataNew) {
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes("createTime", "updateTime");
        AuditInfo auditInfo = new AuditInfo();
        //model名字=LogGroup.定义 方便共用字典
        String service = dataOld.getClass().getSimpleName();
        service = service.replaceAll("([a-z])([A-Z])", "$1" + "_" + "$2").toLowerCase();
        auditInfo.setDataType(service);
        Map<String, Object> oldMap = BeanUtil.beanToMap(dataOld);
        Map<String, Object> newMap = BeanUtil.beanToMap(dataNew);
        for (String key : newMap.keySet()) {
            if (newMap.get(key) == null) {
                //只保存有差异的属性
                oldMap.remove(key);
            }
        }
        auditInfo.setDataOld(JSON.toJSONString(oldMap, excludeFilter));
        auditInfo.setDataNew(JSON.toJSONString(dataNew));
        auditInfo.setApplicant(String.valueOf(RequestContextHolder.getContext().get(AdminContextCacheKey.USER_NAME)));
        auditInfo.setAuditType(LogType.EDIT.code);
        auditInfo.setAuditState(AuditState.WAIT.code);
        return auditInfoMapper.insertSelective(auditInfo);
    }

    /**
     * 删除数据审核
     *
     * @param dataOld 删除前数据
     * @return row
     */
    public int deleteDataAudit(T dataOld) {
        AuditInfo auditInfo = new AuditInfo();
        //model名字=LogGroup.定义 方便共用字典
        String service = dataOld.getClass().getSimpleName();
        service = service.replaceAll("([a-z])([A-Z])", "$1" + "_" + "$2").toLowerCase();
        auditInfo.setDataType(service);
        auditInfo.setDataOld(JSON.toJSONString(dataOld));
        auditInfo.setApplicant(String.valueOf(RequestContextHolder.getContext().get(AdminContextCacheKey.USER_NAME)));
        auditInfo.setAuditType(LogType.DELETE.code);
        auditInfo.setAuditState(AuditState.WAIT.code);
        return auditInfoMapper.insertSelective(auditInfo);
    }

    /**
     * 审核通过后变更数据
     *
     * @param oldObject 更新前数据,如果为空则表示新增
     * @param newObject 更新后数据,如果为空则表示删除
     * @return int
     */
    public int auditingUpdate(T oldObject, T newObject) {
        int row = 0;
        if (oldObject == null) {
            //新增数据
            row = mapper.insertSelective(newObject);
        } else if (newObject == null) {
            //删除数据
            row = mapper.deleteByUk(oldObject);
        } else {
            //修改数据
            T dbObject = mapper.selectByUk(oldObject);
            if (dbObject == null) {
                throw new BizException(CommonReturnCode.DATA_NOT_EXISTS);
            }
            Map<String, Object> dbMap = BeanUtil.beanToMap(dbObject,false,true);
            Map<String, Object> oldMap = BeanUtil.beanToMap(oldObject,false,true);
            for (String key : oldMap.keySet()) {
                if (!oldMap.get(key).equals(dbMap.get(key))) {
                    //检查数据库数据发生变化则不允许操作
                    throw new BizException(CommonReturnCode.DATA_STATE_ERROR);
                }
            }
            //如果原数据只有一个字段表示没有其它字段更新，属于附加关联表更新则主数据不用更新
            if (oldMap.size() != 1) {
                row = mapper.updateByUkSelective(newObject);
            }
            //使用数据库完整的对象回调子类
            oldObject = dbObject;
        }
        auditingNotify(oldObject, newObject);
        return row;
    }

    /**
     * 数据更新后回调实现
     * 触发缓存更新，通知等事件
     *
     * @param oldObject 更新前数据,如果为空则表示新增
     * @param newObject 更新后数据,如果为空则表示删除
     */
    public void auditingNotify(T oldObject, T newObject) {

    }
}
