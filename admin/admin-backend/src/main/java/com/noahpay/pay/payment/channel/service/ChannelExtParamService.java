package com.noahpay.pay.payment.channel.service;

import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.channel.mapper.ChannelExtParamMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelExtParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 渠道扩展参数Service实现类
 *
 * @author kalvan
 */
@Slf4j
@Service("channelExtParamService")
public class ChannelExtParamService extends BaseAuditService<ChannelExtParam> {
    @Resource
    ChannelExtParamMapper mapper;

    @Override
    public void processParams(Map<String, Object> params) {
        // TODO 查询和下载的条件检查
        convertBetweenParams(params, "updateTime", true, false);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(ChannelExtParam channelExtParam) {
        ChannelExtParam channelExtParamQuery = new ChannelExtParam();
       channelExtParamQuery.setChannelNo(channelExtParam.getChannelNo());
       channelExtParamQuery.setChannelMerchantNo(channelExtParam.getChannelMerchantNo());
       channelExtParamQuery.setParamKey(channelExtParam.getParamKey());
        if (mapper.selectCount(channelExtParamQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT);
        }
        return this.addDataAudit(channelExtParam);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(ChannelExtParam channelExtParam) {
        ChannelExtParam channelExtParamDb = mapper.selectByUk(channelExtParam.getId());
        if (channelExtParamDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        // TODO 最小化精准更新,如果全部字段可以更新则直接使用channelExtParam
        ChannelExtParam channelExtParamNew = new ChannelExtParam();
        channelExtParamNew.setId(channelExtParam.getId());
        channelExtParamNew.setChannelNo(channelExtParam.getChannelNo());
        channelExtParamNew.setChannelMerchantNo(channelExtParam.getChannelMerchantNo());
        channelExtParamNew.setParamKey(channelExtParam.getParamKey());
        channelExtParamNew.setParamValue(channelExtParam.getParamValue());
        channelExtParamNew.setRemark(channelExtParam.getRemark());
        return this.editDataAudit(channelExtParamDb, channelExtParamNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.CHANNEL_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            ChannelExtParam channelExtParamDb = mapper.selectByUk(id);
            if (channelExtParamDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(channelExtParamDb);
        }
        return resultNum;
    }
}
