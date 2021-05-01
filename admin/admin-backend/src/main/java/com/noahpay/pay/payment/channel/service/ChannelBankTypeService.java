package com.noahpay.pay.payment.channel.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelBankTypeMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelBankType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道行别对照表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelBankTypeService")
public class ChannelBankTypeService extends BaseAuditService<ChannelBankType> {
    @Resource
    ChannelBankTypeMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelBankType channelBankType) {
        ChannelBankType channelBankTypeQuery = new ChannelBankType();
        channelBankTypeQuery.setChannelNo(channelBankType.getChannelNo());
        channelBankTypeQuery.setPayType(channelBankType.getPayType());
        channelBankTypeQuery.setBankType(channelBankType.getBankType());
        if (mapper.selectCount(channelBankTypeQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(channelBankType);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelBankType channelBankType) {
        ChannelBankType channelBankTypeDb = mapper.selectByUk(channelBankType.getId());
        if (channelBankTypeDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelBankType
        ChannelBankType channelBankTypeNew = new ChannelBankType();
        channelBankTypeNew.setId(channelBankType.getId());
        channelBankTypeNew.setChannelNo(channelBankType.getChannelNo());
        channelBankTypeNew.setPayType(channelBankType.getPayType());
        channelBankTypeNew.setBankType(channelBankType.getBankType());
        channelBankTypeNew.setChannelBankType(channelBankType.getChannelBankType());
        channelBankTypeNew.setRemark(channelBankType.getRemark());
        return this.editDataAudit(channelBankTypeDb, channelBankTypeNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelBankType channelBankTypeDb = mapper.selectByUk(id);
            if (channelBankTypeDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelBankTypeDb);
        }
        return resultNum;
    }
}
