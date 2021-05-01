package com.noahpay.pay.payment.route.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelMultiMerchantMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelMultiMerchant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道收款商户绑定Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelMultiMerchantService")
public class ChannelMultiMerchantService extends BaseAuditService<ChannelMultiMerchant> {
    @Resource
    ChannelMultiMerchantMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelMultiMerchant channelMultiMerchant) {
        ChannelMultiMerchant channelMultiMerchantQuery = new ChannelMultiMerchant();
        channelMultiMerchantQuery.setMerchantNo(channelMultiMerchant.getMerchantNo());
        channelMultiMerchantQuery.setPayType(channelMultiMerchant.getPayType());
        channelMultiMerchantQuery.setChannelMerchantNo(channelMultiMerchant.getChannelMerchantNo());
        if (mapper.selectCount(channelMultiMerchantQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        channelMultiMerchant.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(channelMultiMerchant);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelMultiMerchant channelMultiMerchant) {
        ChannelMultiMerchant channelMultiMerchantDb = mapper.selectByUk(channelMultiMerchant.getId());
        if (channelMultiMerchantDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelMultiMerchant
        ChannelMultiMerchant channelMultiMerchantNew = new ChannelMultiMerchant();
        channelMultiMerchantNew.setId(channelMultiMerchant.getId());
        channelMultiMerchantNew.setChannelNo(channelMultiMerchant.getChannelNo());
        channelMultiMerchantNew.setPayType(channelMultiMerchant.getPayType());
        channelMultiMerchantNew.setMerchantNo(channelMultiMerchant.getMerchantNo());
        channelMultiMerchantNew.setChannelMerchantNo(channelMultiMerchant.getChannelMerchantNo());
        channelMultiMerchantNew.setChannelMerchantName(channelMultiMerchant.getChannelMerchantName());
        channelMultiMerchantNew.setRemark(channelMultiMerchant.getRemark());
        channelMultiMerchantNew.setState(channelMultiMerchant.getState());
        return this.editDataAudit(channelMultiMerchantDb, channelMultiMerchantNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelMultiMerchant channelMultiMerchantDb = mapper.selectByUk(id);
            if (channelMultiMerchantDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelMultiMerchantDb);
        }
        return resultNum;
    }
}
