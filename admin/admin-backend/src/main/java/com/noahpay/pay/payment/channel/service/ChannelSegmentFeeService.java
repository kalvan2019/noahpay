package com.noahpay.pay.payment.channel.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelSegmentFeeMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelSegmentFee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道分段计费配置Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelSegmentFeeService")
public class ChannelSegmentFeeService extends BaseAuditService<ChannelSegmentFee> {
    @Resource
    ChannelSegmentFeeMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelSegmentFee channelSegmentFee) {
        return this.addDataAudit(channelSegmentFee);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelSegmentFee channelSegmentFee) {
        ChannelSegmentFee channelSegmentFeeDb = mapper.selectByUk(channelSegmentFee.getId());
        if (channelSegmentFeeDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelSegmentFee
        ChannelSegmentFee channelSegmentFeeNew = new ChannelSegmentFee();
        channelSegmentFeeNew.setId(channelSegmentFee.getId());
        channelSegmentFeeNew.setFeeSegmentRule(channelSegmentFee.getFeeSegmentRule());
        channelSegmentFeeNew.setBeginAmount(channelSegmentFee.getBeginAmount());
        channelSegmentFeeNew.setEndAmount(channelSegmentFee.getEndAmount());
        channelSegmentFeeNew.setFeeType(channelSegmentFee.getFeeType());
        channelSegmentFeeNew.setFeeRate(channelSegmentFee.getFeeRate());
        channelSegmentFeeNew.setFixFee(channelSegmentFee.getFixFee());
        return this.editDataAudit(channelSegmentFeeDb, channelSegmentFeeNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelSegmentFee channelSegmentFeeDb = mapper.selectByUk(id);
            if (channelSegmentFeeDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelSegmentFeeDb);
        }
        return resultNum;
    }
}
