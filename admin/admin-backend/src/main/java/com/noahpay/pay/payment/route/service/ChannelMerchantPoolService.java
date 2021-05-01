package com.noahpay.pay.payment.route.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelMerchantPoolMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelMerchantPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道商户信息Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelMerchantPoolService")
public class ChannelMerchantPoolService extends BaseAuditService<ChannelMerchantPool> {
    @Resource
    ChannelMerchantPoolMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelMerchantPool channelMerchantPool) {
        ChannelMerchantPool channelMerchantPoolQuery = new ChannelMerchantPool();
        channelMerchantPoolQuery.setChannelNo(channelMerchantPool.getChannelNo());
        channelMerchantPoolQuery.setChannelMerchantNo(channelMerchantPool.getChannelMerchantNo());
        channelMerchantPoolQuery.setChannelSubMerchantNo(channelMerchantPool.getChannelSubMerchantNo());
        channelMerchantPoolQuery.setMerchantNo(channelMerchantPool.getMerchantNo());
        if (mapper.selectCount(channelMerchantPoolQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        channelMerchantPool.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(channelMerchantPool);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelMerchantPool channelMerchantPool) {
        ChannelMerchantPool channelMerchantPoolDb = mapper.selectByUk(channelMerchantPool.getId());
        if (channelMerchantPoolDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelMerchantPool
        ChannelMerchantPool channelMerchantPoolNew = new ChannelMerchantPool();
        channelMerchantPoolNew.setId(channelMerchantPool.getId());
        channelMerchantPoolNew.setChannelNo(channelMerchantPool.getChannelNo());
        channelMerchantPoolNew.setChannelMerchantNo(channelMerchantPool.getChannelMerchantNo());
        channelMerchantPoolNew.setChannelSubMerchantNo(channelMerchantPool.getChannelSubMerchantNo());
        channelMerchantPoolNew.setChannelSubMerchantName(channelMerchantPool.getChannelSubMerchantName());
        channelMerchantPoolNew.setMerchantNo(channelMerchantPool.getMerchantNo());
        channelMerchantPoolNew.setCity(channelMerchantPool.getCity());
        channelMerchantPoolNew.setMcc(channelMerchantPool.getMcc());
        channelMerchantPoolNew.setBeginTime(channelMerchantPool.getBeginTime());
        channelMerchantPoolNew.setEndTime(channelMerchantPool.getEndTime());
        channelMerchantPoolNew.setLimitMinAmount(channelMerchantPool.getLimitMinAmount());
        channelMerchantPoolNew.setLimitMaxAmount(channelMerchantPool.getLimitMaxAmount());
        channelMerchantPoolNew.setDayUseAmount(channelMerchantPool.getDayUseAmount());
        channelMerchantPoolNew.setDayLimitAmount(channelMerchantPool.getDayLimitAmount());
        channelMerchantPoolNew.setMonthUseAmount(channelMerchantPool.getMonthUseAmount());
        channelMerchantPoolNew.setMonthLimitAmount(channelMerchantPool.getMonthLimitAmount());
        channelMerchantPoolNew.setState(channelMerchantPool.getState());
        channelMerchantPoolNew.setRemark(channelMerchantPool.getRemark());
        return this.editDataAudit(channelMerchantPoolDb, channelMerchantPoolNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelMerchantPool channelMerchantPoolDb = mapper.selectByUk(id);
            if (channelMerchantPoolDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelMerchantPoolDb);
        }
        return resultNum;
    }
}
