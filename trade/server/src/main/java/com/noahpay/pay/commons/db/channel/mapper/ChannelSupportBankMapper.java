package com.noahpay.pay.commons.db.channel.mapper;

import com.noahpay.pay.commons.db.channel.model.ChannelSupportBank;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通道支持银行Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface ChannelSupportBankMapper extends IBaseMapper<ChannelSupportBank> {
    List<ChannelSupportBank> querySupportBank(@Param("channelNo") Integer channelNo, @Param("payType") String payType);

}
