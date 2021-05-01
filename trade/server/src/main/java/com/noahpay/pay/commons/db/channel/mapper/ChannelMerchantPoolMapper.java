package com.noahpay.pay.commons.db.channel.mapper;

import com.noahpay.pay.commons.db.channel.model.ChannelMerchantPool;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通道商户信息Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface ChannelMerchantPoolMapper extends IBaseMapper<ChannelMerchantPool> {
    List<ChannelMerchantPool> queryChannelMerchantPool(@Param("poolRouteList") List<Integer> poolRouteList,
                                                       @Param("channelMerchantNo") String channelMerchantNo,
                                                       @Param("orderAmount") Long orderAmount,
                                                       @Param("city") Integer city,
                                                       @Param("mcc") Integer mcc,
                                                       @Param("merchantNo") Long merchantNo);

    int updateChannelMerchantUse(@Param("channelMerchantPool") ChannelMerchantPool channelMerchantPool);

    int resetChannelMerchantUse(@Param("resetMonthUse") boolean resetMonthUse);
}
