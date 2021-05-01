package com.noahpay.pay.payment.route.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelSupportBankTypeGroupMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelSupportBankTypeGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道支持银行卡类型组Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelSupportBankTypeGroupService")
public class ChannelSupportBankTypeGroupService extends BaseAuditService<ChannelSupportBankTypeGroup> {
    @Resource
    ChannelSupportBankTypeGroupMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelSupportBankTypeGroup channelSupportBankTypeGroup) {
        ChannelSupportBankTypeGroup channelSupportBankTypeGroupQuery = new ChannelSupportBankTypeGroup();
        channelSupportBankTypeGroupQuery.setBankTypeGroup(channelSupportBankTypeGroup.getBankTypeGroup());
        channelSupportBankTypeGroupQuery.setBankType(channelSupportBankTypeGroup.getBankType());
        if (mapper.selectCount(channelSupportBankTypeGroupQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        channelSupportBankTypeGroup.setState(SwitchEnum.OPEN.code);
        return mapper.insertSelective(channelSupportBankTypeGroup);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelSupportBankTypeGroup channelSupportBankTypeGroup) {
        ChannelSupportBankTypeGroup channelSupportBankTypeGroupDb = mapper.selectByUk(channelSupportBankTypeGroup.getId());
        if (channelSupportBankTypeGroupDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelSupportBankTypeGroup
        ChannelSupportBankTypeGroup channelSupportBankTypeGroupNew = new ChannelSupportBankTypeGroup();
        channelSupportBankTypeGroupNew.setId(channelSupportBankTypeGroup.getId());
        channelSupportBankTypeGroupNew.setBankTypeGroup(channelSupportBankTypeGroup.getBankTypeGroup());
        channelSupportBankTypeGroupNew.setBankType(channelSupportBankTypeGroup.getBankType());
        channelSupportBankTypeGroupNew.setState(channelSupportBankTypeGroup.getState());
        return mapper.updateByUkSelective(channelSupportBankTypeGroupNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelSupportBankTypeGroup channelSupportBankTypeGroupDb = mapper.selectByUk(id);
            if (channelSupportBankTypeGroupDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + mapper.deleteByUk(channelSupportBankTypeGroupDb);
        }
        return resultNum;
    }
}
