package com.noahpay.pay.payment.channel.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelCertificateTypeMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelCertificateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道证件类型对照表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelCertificateTypeService")
public class ChannelCertificateTypeService extends BaseAuditService<ChannelCertificateType> {
    @Resource
    ChannelCertificateTypeMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelCertificateType channelCertificateType) {
        ChannelCertificateType channelCertificateTypeQuery = new ChannelCertificateType();
        channelCertificateTypeQuery.setChannelNo(channelCertificateType.getChannelNo());
        channelCertificateTypeQuery.setPayType(channelCertificateType.getPayType());
        channelCertificateTypeQuery.setCertificateType(channelCertificateType.getCertificateType());
        if (mapper.selectCount(channelCertificateTypeQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(channelCertificateType);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelCertificateType channelCertificateType) {
        ChannelCertificateType channelCertificateTypeDb = mapper.selectByUk(channelCertificateType.getId());
        if (channelCertificateTypeDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelCertificateType
        ChannelCertificateType channelCertificateTypeNew = new ChannelCertificateType();
        channelCertificateTypeNew.setId(channelCertificateType.getId());
        channelCertificateTypeNew.setChannelNo(channelCertificateType.getChannelNo());
        channelCertificateTypeNew.setPayType(channelCertificateType.getPayType());
        channelCertificateTypeNew.setCertificateType(channelCertificateType.getCertificateType());
        channelCertificateTypeNew.setChannelCertificateType(channelCertificateType.getChannelCertificateType());
        channelCertificateTypeNew.setRemark(channelCertificateType.getRemark());
        return this.editDataAudit(channelCertificateTypeDb, channelCertificateTypeNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelCertificateType channelCertificateTypeDb = mapper.selectByUk(id);
            if (channelCertificateTypeDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelCertificateTypeDb);
        }
        return resultNum;
    }
}
