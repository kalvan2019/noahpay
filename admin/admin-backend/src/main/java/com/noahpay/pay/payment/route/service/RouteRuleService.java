package com.noahpay.pay.payment.route.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.RouteRuleMapper;
import com.noahpay.pay.commons.db.channel.model.RouteRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 路由规则配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("routeRuleService")
public class RouteRuleService extends BaseAuditService<RouteRule> {
    @Resource
    RouteRuleMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(RouteRule routeRule) {
        RouteRule routeRuleQuery = new RouteRule();
        routeRuleQuery.setRouteRule(routeRule.getRouteRule());
        routeRuleQuery.setChannelNo(routeRule.getChannelNo());
        routeRuleQuery.setBankType(routeRule.getBankType());
        routeRuleQuery.setLimitMinAmount(routeRule.getLimitMinAmount());
        if (mapper.selectCount(routeRuleQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        routeRule.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(routeRule);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(RouteRule routeRule) {
        RouteRule routeRuleDb = mapper.selectByUk(routeRule.getId());
        if (routeRuleDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用routeRule
        RouteRule routeRuleNew = new RouteRule();
        routeRuleNew.setId(routeRule.getId());
        routeRuleNew.setRouteRule(routeRule.getRouteRule());
        routeRuleNew.setChannelNo(routeRule.getChannelNo());
        routeRuleNew.setBankType(routeRule.getBankType());
        routeRuleNew.setBankAccountType(routeRule.getBankAccountType());
        routeRuleNew.setLimitMinAmount(routeRule.getLimitMinAmount());
        routeRuleNew.setLimitMaxAmount(routeRule.getLimitMaxAmount());
        routeRuleNew.setBeginDate(routeRule.getBeginDate());
        routeRuleNew.setEndDate(routeRule.getEndDate());
        routeRuleNew.setBeginTime(routeRule.getBeginTime());
        routeRuleNew.setEndTime(routeRule.getEndTime());
        routeRuleNew.setPriority(routeRule.getPriority());
        routeRuleNew.setWeight(routeRule.getWeight());
        routeRuleNew.setRemark(routeRule.getRemark());
        routeRuleNew.setState(routeRule.getState());
        return this.editDataAudit(routeRuleDb, routeRuleNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            RouteRule routeRuleDb = mapper.selectByUk(id);
            if (routeRuleDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(routeRuleDb);
        }
        return resultNum;
    }
}
