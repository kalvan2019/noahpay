package com.noahpay.pay.payment.route.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelSupportBankMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelSupportBank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道支持银行Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelSupportBankService")
public class ChannelSupportBankService extends BaseAuditService<ChannelSupportBank> {
    @Resource
    ChannelSupportBankMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelSupportBank channelSupportBank) {
        ChannelSupportBank channelSupportBankQuery = new ChannelSupportBank();
        channelSupportBankQuery.setChannelNo(channelSupportBank.getChannelNo());
        channelSupportBankQuery.setPayType(channelSupportBank.getPayType());
        channelSupportBankQuery.setBankTypeGroup(channelSupportBank.getBankTypeGroup());
        if (mapper.selectCount(channelSupportBankQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        channelSupportBank.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(channelSupportBank);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelSupportBank channelSupportBank) {
        ChannelSupportBank channelSupportBankDb = mapper.selectByUk(channelSupportBank.getId());
        if (channelSupportBankDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelSupportBank
        ChannelSupportBank channelSupportBankNew = new ChannelSupportBank();
        channelSupportBankNew.setId(channelSupportBank.getId());
        channelSupportBankNew.setChannelNo(channelSupportBank.getChannelNo());
        channelSupportBankNew.setPayType(channelSupportBank.getPayType());
        channelSupportBankNew.setLimitMinAmount(channelSupportBank.getLimitMinAmount());
        channelSupportBankNew.setLimitMaxAmount(channelSupportBank.getLimitMaxAmount());
        channelSupportBankNew.setBeginTime(channelSupportBank.getBeginTime());
        channelSupportBankNew.setEndTime(channelSupportBank.getEndTime());
        channelSupportBankNew.setBankAccountType(channelSupportBank.getBankAccountType());
        channelSupportBankNew.setBankTypeGroup(channelSupportBank.getBankTypeGroup());
        channelSupportBankNew.setBankCityGroup(channelSupportBank.getBankCityGroup());
        channelSupportBankNew.setBankSupport(channelSupportBank.getBankSupport());
        channelSupportBankNew.setRemark(channelSupportBank.getRemark());
        channelSupportBankNew.setState(channelSupportBank.getState());
        return this.editDataAudit(channelSupportBankDb, channelSupportBankNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelSupportBank channelSupportBankDb = mapper.selectByUk(id);
            if (channelSupportBankDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelSupportBankDb);
        }
        return resultNum;
    }
}
