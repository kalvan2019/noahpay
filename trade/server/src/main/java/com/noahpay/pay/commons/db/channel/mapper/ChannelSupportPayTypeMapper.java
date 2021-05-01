package com.noahpay.pay.commons.db.channel.mapper;

import com.noahpay.pay.commons.db.channel.model.ChannelSupportPayType;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 通道支持支付方式Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface ChannelSupportPayTypeMapper extends IBaseMapper<ChannelSupportPayType> {

    void updateChannelPayTypeUse(@Param("channelSupportPayType") ChannelSupportPayType channelSupportPayType);

    void resetChannelPayTypeUse();
}
