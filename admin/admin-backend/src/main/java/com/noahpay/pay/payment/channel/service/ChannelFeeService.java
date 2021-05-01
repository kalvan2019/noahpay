package com.noahpay.pay.payment.channel.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelFeeMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelFee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道成本费率配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelFeeService")
public class ChannelFeeService extends BaseAuditService<ChannelFee> {
    @Resource
    ChannelFeeMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelFee channelFee) {
        ChannelFee channelFeeQuery = new ChannelFee();
        channelFeeQuery.setChannelNo(channelFee.getChannelNo());
        channelFeeQuery.setPayType(channelFee.getPayType());
        channelFeeQuery.setBankType(channelFee.getBankType());
        channelFeeQuery.setBankAccountType(channelFee.getBankAccountType());
        if (mapper.selectCount(channelFeeQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(channelFee);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelFee channelFee) {
        ChannelFee channelFeeDb = mapper.selectByUk(channelFee.getId());
        if (channelFeeDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelFee
        ChannelFee channelFeeNew = new ChannelFee();
        channelFeeNew.setId(channelFee.getId());
        channelFeeNew.setChannelNo(channelFee.getChannelNo());
        channelFeeNew.setPayType(channelFee.getPayType());
        channelFeeNew.setBankType(channelFee.getBankType());
        channelFeeNew.setBankAccountType(channelFee.getBankAccountType());
        channelFeeNew.setFeeType(channelFee.getFeeType());
        channelFeeNew.setFeeRate(channelFee.getFeeRate());
        channelFeeNew.setFixFee(channelFee.getFixFee());
        channelFeeNew.setMinFee(channelFee.getMinFee());
        channelFeeNew.setFeeSegmentRule(channelFee.getFeeSegmentRule());
        return this.editDataAudit(channelFeeDb, channelFeeNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelFee channelFeeDb = mapper.selectByUk(id);
            if (channelFeeDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelFeeDb);
        }
        return resultNum;
    }
}
