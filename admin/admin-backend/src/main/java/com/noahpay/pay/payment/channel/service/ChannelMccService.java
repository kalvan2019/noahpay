package com.noahpay.pay.payment.channel.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelMccMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelMcc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道行业类别转换对照表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelMccService")
public class ChannelMccService extends BaseAuditService<ChannelMcc> {
    @Resource
    ChannelMccMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelMcc channelMcc) {
        ChannelMcc channelMccQuery = new ChannelMcc();
        channelMccQuery.setChannelNo(channelMcc.getChannelNo());
        channelMccQuery.setPayType(channelMcc.getPayType());
        channelMccQuery.setMcc(channelMcc.getMcc());
        if (mapper.selectCount(channelMccQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(channelMcc);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelMcc channelMcc) {
        ChannelMcc channelMccDb = mapper.selectByUk(channelMcc.getId());
        if (channelMccDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelMcc
        ChannelMcc channelMccNew = new ChannelMcc();
        channelMccNew.setId(channelMcc.getId());
        channelMccNew.setChannelNo(channelMcc.getChannelNo());
        channelMccNew.setPayType(channelMcc.getPayType());
        channelMccNew.setMcc(channelMcc.getMcc());
        channelMccNew.setChannelMcc(channelMcc.getChannelMcc());
        channelMccNew.setRemark(channelMcc.getRemark());
        return this.editDataAudit(channelMccDb, channelMccNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelMcc channelMccDb = mapper.selectByUk(id);
            if (channelMccDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelMccDb);
        }
        return resultNum;
    }
}
