package com.noahpay.pay.payment.route.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.RouteMerchantMapper;
import com.noahpay.pay.commons.db.channel.model.RouteMerchant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 商户路由配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("routeMerchantService")
public class RouteMerchantService extends BaseAuditService<RouteMerchant> {
    @Resource
    RouteMerchantMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "createTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(RouteMerchant routeMerchant) {
        RouteMerchant routeMerchantQuery = new RouteMerchant();
        routeMerchantQuery.setMerchantNo(routeMerchant.getMerchantNo());
        routeMerchantQuery.setPayType(routeMerchant.getPayType());
        if (mapper.selectCount(routeMerchantQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        routeMerchant.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(routeMerchant);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(RouteMerchant routeMerchant) {
        RouteMerchant routeMerchantDb = mapper.selectByUk(routeMerchant.getId());
        if (routeMerchantDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用routeMerchant
        RouteMerchant routeMerchantNew = new RouteMerchant();
        routeMerchantNew.setId(routeMerchant.getId());
        routeMerchantNew.setMerchantNo(routeMerchant.getMerchantNo());
        routeMerchantNew.setPayType(routeMerchant.getPayType());
        routeMerchantNew.setChannelNo(routeMerchant.getChannelNo());
        routeMerchantNew.setRouteRule(routeMerchant.getRouteRule());
        routeMerchantNew.setState(routeMerchant.getState());
        return this.editDataAudit(routeMerchantDb, routeMerchantNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            RouteMerchant routeMerchantDb = mapper.selectByUk(id);
            if (routeMerchantDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(routeMerchantDb);
        }
        return resultNum;
    }
}
