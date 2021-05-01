package com.noahpay.pay.payment.route.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelSupportBankCityGroupMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelSupportBankCityGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道支持地区组Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelSupportBankCityGroupService")
public class ChannelSupportBankCityGroupService extends BaseAuditService<ChannelSupportBankCityGroup> {
    @Resource
    ChannelSupportBankCityGroupMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelSupportBankCityGroup channelSupportBankCityGroup) {
        ChannelSupportBankCityGroup channelSupportBankCityGroupQuery = new ChannelSupportBankCityGroup();
        channelSupportBankCityGroupQuery.setBankCityGroup(channelSupportBankCityGroup.getBankCityGroup());
        channelSupportBankCityGroupQuery.setBankCity(channelSupportBankCityGroup.getBankCity());
        if (mapper.selectCount(channelSupportBankCityGroupQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        channelSupportBankCityGroup.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(channelSupportBankCityGroup);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelSupportBankCityGroup channelSupportBankCityGroup) {
        ChannelSupportBankCityGroup channelSupportBankCityGroupDb = mapper.selectByUk(channelSupportBankCityGroup.getId());
        if (channelSupportBankCityGroupDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelSupportBankCityGroup
        ChannelSupportBankCityGroup channelSupportBankCityGroupNew = new ChannelSupportBankCityGroup();
        channelSupportBankCityGroupNew.setId(channelSupportBankCityGroup.getId());
        channelSupportBankCityGroupNew.setBankCityGroup(channelSupportBankCityGroup.getBankCityGroup());
        channelSupportBankCityGroupNew.setBankCity(channelSupportBankCityGroup.getBankCity());
        channelSupportBankCityGroupNew.setState(channelSupportBankCityGroup.getState());
        return this.editDataAudit(channelSupportBankCityGroupDb, channelSupportBankCityGroupNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelSupportBankCityGroup channelSupportBankCityGroupDb = mapper.selectByUk(id);
            if (channelSupportBankCityGroupDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelSupportBankCityGroupDb);
        }
        return resultNum;
    }
}
