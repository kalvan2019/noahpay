package com.noahpay.pay.payment.channel.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelBankAccountTypeMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelBankAccountType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道卡类型对照表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelBankAccountTypeService")
public class ChannelBankAccountTypeService extends BaseAuditService<ChannelBankAccountType> {
    @Resource
    ChannelBankAccountTypeMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelBankAccountType channelBankAccountType) {
        ChannelBankAccountType channelBankAccountTypeQuery = new ChannelBankAccountType();
        channelBankAccountTypeQuery.setChannelNo(channelBankAccountType.getChannelNo());
        channelBankAccountTypeQuery.setPayType(channelBankAccountType.getPayType());
        channelBankAccountTypeQuery.setBankAccountType(channelBankAccountType.getBankAccountType());
        if (mapper.selectCount(channelBankAccountTypeQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(channelBankAccountType);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelBankAccountType channelBankAccountType) {
        ChannelBankAccountType channelBankAccountTypeDb = mapper.selectByUk(channelBankAccountType.getId());
        if (channelBankAccountTypeDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelBankAccountType
        ChannelBankAccountType channelBankAccountTypeNew = new ChannelBankAccountType();
        channelBankAccountTypeNew.setId(channelBankAccountType.getId());
        channelBankAccountTypeNew.setChannelNo(channelBankAccountType.getChannelNo());
        channelBankAccountTypeNew.setPayType(channelBankAccountType.getPayType());
        channelBankAccountTypeNew.setBankAccountType(channelBankAccountType.getBankAccountType());
        channelBankAccountTypeNew.setChannelBankAccountType(channelBankAccountType.getChannelBankAccountType());
        channelBankAccountTypeNew.setRemark(channelBankAccountType.getRemark());
        return this.editDataAudit(channelBankAccountTypeDb, channelBankAccountTypeNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelBankAccountType channelBankAccountTypeDb = mapper.selectByUk(id);
            if (channelBankAccountTypeDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelBankAccountTypeDb);
        }
        return resultNum;
    }
}
