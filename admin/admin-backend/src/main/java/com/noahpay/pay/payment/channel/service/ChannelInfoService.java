package com.noahpay.pay.payment.channel.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelInfoMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道列表Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelInfoService")
public class ChannelInfoService extends BaseAuditService<ChannelInfo> {
    @Resource
    ChannelInfoMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelInfo channelInfo) {
        ChannelInfo channelInfoQuery = new ChannelInfo();
        // TODO 填充数据,选择字段进行检查，更新人，更新时间，查重
        if (mapper.selectCount(channelInfoQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        channelInfo.setState(SwitchEnum.OPEN.code);
        return this.addDataAudit(channelInfo);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelInfo channelInfo) {
        ChannelInfo channelInfoDb = mapper.selectByUk(channelInfo.getId());
        if (channelInfoDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelInfo
        ChannelInfo channelInfoNew = new ChannelInfo();
        channelInfoNew.setId(channelInfo.getId());
        channelInfoNew.setChannelNo(channelInfo.getChannelNo());
        channelInfoNew.setChannelName(channelInfo.getChannelName());
        channelInfoNew.setChannelClass(channelInfo.getChannelClass());
        channelInfoNew.setChannelMerchantNo(channelInfo.getChannelMerchantNo());
        channelInfoNew.setMerchantPoolConvert(channelInfo.getMerchantPoolConvert());
        channelInfoNew.setMultiMerchantConvert(channelInfo.getMultiMerchantConvert());
        channelInfoNew.setSendSnConvert(channelInfo.getSendSnConvert());
        channelInfoNew.setBankTypeConvert(channelInfo.getBankTypeConvert());
        channelInfoNew.setBankAccountTypeConvert(channelInfo.getBankAccountTypeConvert());
        channelInfoNew.setCertificateTypeConvert(channelInfo.getCertificateTypeConvert());
        channelInfoNew.setCityConvert(channelInfo.getCityConvert());
        channelInfoNew.setMccConvert(channelInfo.getMccConvert());
        channelInfoNew.setCustConvert(channelInfo.getCustConvert());
        channelInfoNew.setCheckSupport(channelInfo.getCheckSupport());
        channelInfoNew.setCheckTime(channelInfo.getCheckTime());
        channelInfoNew.setCheckField(channelInfo.getCheckField());
        channelInfoNew.setSettlementTime(channelInfo.getSettlementTime());
        channelInfoNew.setSettlementBankAccountNo(channelInfo.getSettlementBankAccountNo());
        channelInfoNew.setSettlementBankAccountName(channelInfo.getSettlementBankAccountName());
        channelInfoNew.setSettlementBankType(channelInfo.getSettlementBankType());
        channelInfoNew.setState(channelInfo.getState());
        return this.editDataAudit(channelInfoDb, channelInfoNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelInfo channelInfoDb = mapper.selectByUk(id);
            if (channelInfoDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelInfoDb);
        }
        return resultNum;
    }
}
