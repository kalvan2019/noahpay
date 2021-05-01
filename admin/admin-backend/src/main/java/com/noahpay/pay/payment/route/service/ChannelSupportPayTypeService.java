package com.noahpay.pay.payment.route.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelSupportPayTypeMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelSupportPayType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道支持支付方式Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelSupportPayTypeService")
public class ChannelSupportPayTypeService extends BaseAuditService<ChannelSupportPayType> {
    @Resource
    ChannelSupportPayTypeMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "lastDate", false, false);
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelSupportPayType channelSupportPayType) {
        ChannelSupportPayType channelSupportPayTypeQuery = new ChannelSupportPayType();
        channelSupportPayTypeQuery.setChannelNo(channelSupportPayType.getChannelNo());
        channelSupportPayTypeQuery.setPayType(channelSupportPayType.getPayType());
        if (mapper.selectCount(channelSupportPayTypeQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        channelSupportPayType.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(channelSupportPayType);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelSupportPayType channelSupportPayType) {
        ChannelSupportPayType channelSupportPayTypeDb = mapper.selectByUk(channelSupportPayType.getId());
        if (channelSupportPayTypeDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelSupportPayType
        ChannelSupportPayType channelSupportPayTypeNew = new ChannelSupportPayType();
        channelSupportPayTypeNew.setId(channelSupportPayType.getId());
        channelSupportPayTypeNew.setChannelNo(channelSupportPayType.getChannelNo());
        channelSupportPayTypeNew.setPayType(channelSupportPayType.getPayType());
        channelSupportPayTypeNew.setDayUseAmount(channelSupportPayType.getDayUseAmount());
        channelSupportPayTypeNew.setDayLimitAmount(channelSupportPayType.getDayLimitAmount());
        channelSupportPayTypeNew.setDayUseNumber(channelSupportPayType.getDayUseNumber());
        channelSupportPayTypeNew.setDayLimitNumber(channelSupportPayType.getDayLimitNumber());
        channelSupportPayTypeNew.setMonthUseAmount(channelSupportPayType.getMonthUseAmount());
        channelSupportPayTypeNew.setMonthLimitAmount(channelSupportPayType.getMonthLimitAmount());
        channelSupportPayTypeNew.setLastDate(channelSupportPayType.getLastDate());
        channelSupportPayTypeNew.setHolidaySupport(channelSupportPayType.getHolidaySupport());
        channelSupportPayTypeNew.setBankRouteEnabled(channelSupportPayType.getBankRouteEnabled());
        channelSupportPayTypeNew.setState(channelSupportPayType.getState());
        return this.editDataAudit(channelSupportPayTypeDb, channelSupportPayTypeNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelSupportPayType channelSupportPayTypeDb = mapper.selectByUk(id);
            if (channelSupportPayTypeDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelSupportPayTypeDb);
        }
        return resultNum;
    }
}
