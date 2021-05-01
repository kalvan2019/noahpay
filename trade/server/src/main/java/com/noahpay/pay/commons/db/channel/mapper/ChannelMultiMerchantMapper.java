package com.noahpay.pay.commons.db.channel.mapper;

import com.noahpay.pay.commons.db.channel.model.ChannelMultiMerchant;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 通道收款商户绑定Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface ChannelMultiMerchantMapper extends IBaseMapper<ChannelMultiMerchant> {

    ChannelMultiMerchant queryChannelMultiMerchant(@Param("merchantNo") Long merchantNo,
                                                   @Param("payType") String payType,
                                                   @Param("channelMerchantNo") String channelMerchantNo);
}
