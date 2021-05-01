package com.noahpay.pay.route.service;

import com.kalvan.client.model.Response;
import com.kalvan.web.util.DateUtil;
import com.noahpay.pay.commons.db.channel.mapper.ChannelMerchantPoolMapper;
import com.noahpay.pay.commons.db.channel.mapper.ChannelSupportPayTypeMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelMerchantPool;
import com.noahpay.pay.commons.db.channel.model.ChannelSupportPayType;
import com.noahpay.pay.route.bean.req.ChannelUseRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Service
@Slf4j
public class ChannelService {
    /**
     * 通配符
     */
    public static final String WILD_CARDS = "*";
    @Resource
    private ChannelMerchantPoolMapper channelMerchantPoolMapper;
    @Resource
    private ChannelSupportPayTypeMapper channelSupportPayTypeMapper;
    @Resource
    CacheChannelService cacheChannelService;

    public String getChannelExtParamValue(Integer channelNo, String channelMerchantNo, String paramKey) {
        Map<String, Map<String, String>> channelParamMap = cacheChannelService.getChannelExtParam(channelNo);
        Map<String, String> channelMerchantMap = channelParamMap.get(channelMerchantNo);
        String paramValue = "";
        if (channelMerchantMap != null) {
            paramValue = channelMerchantMap.get(paramKey);
        }
        if (StringUtils.isBlank(paramValue) && channelParamMap.get(WILD_CARDS) != null) {
            paramValue = channelParamMap.get(WILD_CARDS).get(paramKey);
        }
        return paramValue;
    }

    public Response updateChannelUse(ChannelUseRequest request) {
        //更新通道商户使用额
        if (request.getChannelMerchantUseAmount() != null) {
            for (Map.Entry<Integer, Map<String, Map<String, Long>>> channelEntry : request.getChannelMerchantUseAmount().entrySet()) {
                for (Map.Entry<String, Map<String, Long>> merchantEntry : channelEntry.getValue().entrySet()) {
                    for (Map.Entry<String, Long> subMerchantEntry : merchantEntry.getValue().entrySet()) {
                        ChannelMerchantPool channelMerchantPool = new ChannelMerchantPool();
                        channelMerchantPool.setChannelNo(channelEntry.getKey());
                        channelMerchantPool.setChannelMerchantNo(merchantEntry.getKey());
                        channelMerchantPool.setChannelSubMerchantNo(subMerchantEntry.getKey());
                        channelMerchantPool.setDayUseAmount(subMerchantEntry.getValue());
                        channelMerchantPoolMapper.updateChannelMerchantUse(channelMerchantPool);
                    }
                }
            }
        }
        //更新通道支付方式使用额
        if (request.getChannelUseAmount() != null) {
            for (Map.Entry<String, Map<Integer, Long>> payTypeEntry : request.getChannelUseAmount().entrySet()) {
                Map<Integer, Long> channelUseNumberMap = request.getChannelUseNumber().get(payTypeEntry.getKey());
                for (Map.Entry<Integer, Long> channelEntry : payTypeEntry.getValue().entrySet()) {
                    ChannelSupportPayType channelSupportPayType = new ChannelSupportPayType();
                    channelSupportPayType.setChannelNo(channelEntry.getKey());
                    channelSupportPayType.setPayType(payTypeEntry.getKey());
                    channelSupportPayType.setDayUseAmount(channelEntry.getValue());
                    channelSupportPayType.setDayUseNumber(channelUseNumberMap.get(channelEntry.getKey()));
                    channelSupportPayTypeMapper.updateChannelPayTypeUse(channelSupportPayType);
                }
            }
        }
        return Response.buildSuccess();
    }

    public Response resetChannelUse() {
        Date now = DateUtil.date();
        Date endOfMonthDate = DateUtil.endOfMonth(DateUtil.date());
        boolean resetMonthUse = false;
        if (DateUtil.isSameDay(now, endOfMonthDate)) {
            log.info("重置商户月使用额");
            resetMonthUse = true;
        }
        //重置通道商户使用额
        while (true) {
            int row = channelMerchantPoolMapper.resetChannelMerchantUse(resetMonthUse);
            log.info("重置商户使用额记录条数:{}", row);
            if (row == 0) {
                break;
            }
        }
        //重置通道支付方式使用额
        channelSupportPayTypeMapper.resetChannelPayTypeUse();
        return Response.buildSuccess();
    }
}
