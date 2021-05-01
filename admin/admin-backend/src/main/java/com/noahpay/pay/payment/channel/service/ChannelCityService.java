package com.noahpay.pay.payment.channel.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelCityMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelCity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道城市代码对照表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelCityService")
public class ChannelCityService extends BaseAuditService<ChannelCity> {
    @Resource
    ChannelCityMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelCity channelCity) {
        ChannelCity channelCityQuery = new ChannelCity();
        channelCityQuery.setChannelNo(channelCity.getChannelNo());
        channelCityQuery.setPayType(channelCity.getPayType());
        channelCityQuery.setCity(channelCity.getCity());
        if (mapper.selectCount(channelCityQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(channelCity);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelCity channelCity) {
        ChannelCity channelCityDb = mapper.selectByUk(channelCity.getId());
        if (channelCityDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelCity
        ChannelCity channelCityNew = new ChannelCity();
        channelCityNew.setId(channelCity.getId());
        channelCityNew.setChannelNo(channelCity.getChannelNo());
        channelCityNew.setPayType(channelCity.getPayType());
        channelCityNew.setCity(channelCity.getCity());
        channelCityNew.setChannelCity(channelCity.getChannelCity());
        channelCityNew.setRemark(channelCity.getRemark());
        return this.editDataAudit(channelCityDb, channelCityNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelCity channelCityDb = mapper.selectByUk(id);
            if (channelCityDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelCityDb);
        }
        return resultNum;
    }
}
