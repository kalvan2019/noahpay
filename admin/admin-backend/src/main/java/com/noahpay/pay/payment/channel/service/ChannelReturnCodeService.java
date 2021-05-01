package com.noahpay.pay.payment.channel.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelReturnCodeMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道返回码Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelReturnCodeService")
public class ChannelReturnCodeService extends BaseAuditService<ChannelReturnCode> {
    @Resource
    ChannelReturnCodeMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelReturnCode channelReturnCode) {
        ChannelReturnCode channelReturnCodeQuery = new ChannelReturnCode();
        channelReturnCodeQuery.setChannelNo(channelReturnCode.getChannelNo());
        channelReturnCodeQuery.setPayType(channelReturnCode.getPayType());
        channelReturnCodeQuery.setChannelReturnCode(channelReturnCode.getChannelReturnCode());
        if (mapper.selectCount(channelReturnCodeQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(channelReturnCode);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelReturnCode channelReturnCode) {
        ChannelReturnCode channelReturnCodeDb = mapper.selectByUk(channelReturnCode.getId());
        if (channelReturnCodeDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelReturnCode
        ChannelReturnCode channelReturnCodeNew = new ChannelReturnCode();
        channelReturnCodeNew.setId(channelReturnCode.getId());
        channelReturnCodeNew.setChannelNo(channelReturnCode.getChannelNo());
        channelReturnCodeNew.setPayType(channelReturnCode.getPayType());
        channelReturnCodeNew.setChannelReturnCode(channelReturnCode.getChannelReturnCode());
        channelReturnCodeNew.setChannelResultNote(channelReturnCode.getChannelResultNote());
        channelReturnCodeNew.setReturnCode(channelReturnCode.getReturnCode());
        channelReturnCodeNew.setReturnNote(channelReturnCode.getReturnNote());
        channelReturnCodeNew.setTransState(channelReturnCode.getTransState());
        return this.editDataAudit(channelReturnCodeDb, channelReturnCodeNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelReturnCode channelReturnCodeDb = mapper.selectByUk(id);
            if (channelReturnCodeDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelReturnCodeDb);
        }
        return resultNum;
    }
}
