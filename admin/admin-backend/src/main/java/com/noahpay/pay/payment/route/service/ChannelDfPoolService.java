package com.noahpay.pay.payment.route.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelDfPoolMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelDfPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道结算商户绑定Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelDfPoolService")
public class ChannelDfPoolService extends BaseAuditService<ChannelDfPool> {
    @Resource
    ChannelDfPoolMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "availableAmount", false, false);
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelDfPool channelDfPool) {
        ChannelDfPool channelDfPoolQuery = new ChannelDfPool();
        channelDfPoolQuery.setChannelNo(channelDfPool.getChannelNo());
        channelDfPoolQuery.setChannelMerchantNo(channelDfPool.getChannelMerchantNo());
        if (mapper.selectCount(channelDfPoolQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        channelDfPool.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(channelDfPool);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelDfPool channelDfPool) {
        ChannelDfPool channelDfPoolDb = mapper.selectByUk(channelDfPool.getId());
        if (channelDfPoolDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelDfPool
        ChannelDfPool channelDfPoolNew = new ChannelDfPool();
        channelDfPoolNew.setId(channelDfPool.getId());
        channelDfPoolNew.setChannelNo(channelDfPool.getChannelNo());
        channelDfPoolNew.setMerchantNo(channelDfPool.getMerchantNo());
        channelDfPoolNew.setChannelMerchantNo(channelDfPool.getChannelMerchantNo());
        channelDfPoolNew.setChannelMerchantName(channelDfPool.getChannelMerchantName());
        channelDfPoolNew.setAmount(channelDfPool.getAmount());
        channelDfPoolNew.setFreezeAmount(channelDfPool.getFreezeAmount());
        channelDfPoolNew.setAvailableAmount(channelDfPool.getAvailableAmount());
        channelDfPoolNew.setState(channelDfPool.getState());
        channelDfPoolNew.setRemark(channelDfPool.getRemark());
        return this.editDataAudit(channelDfPoolDb, channelDfPoolNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelDfPool channelDfPoolDb = mapper.selectByUk(id);
            if (channelDfPoolDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelDfPoolDb);
        }
        return resultNum;
    }
}
