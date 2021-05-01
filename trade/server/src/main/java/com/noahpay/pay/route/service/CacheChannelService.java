package com.noahpay.pay.route.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.noahpay.pay.commons.db.channel.mapper.ChannelExtParamMapper;
import com.noahpay.pay.commons.db.channel.mapper.ChannelInfoMapper;
import com.noahpay.pay.commons.db.channel.model.ChannelExtParam;
import com.noahpay.pay.commons.db.channel.model.ChannelInfo;
import com.kalvan.enums.cache.CacheTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Service
@Slf4j
public class CacheChannelService {

    @Resource
    private ChannelInfoMapper channelInfoMapper;
    @Resource
    private ChannelExtParamMapper channelExtParamMapper;

    @Cached(name = CacheTypeEnum.CHANNEL_INFO, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public ChannelInfo getChannelInfo(Integer channelNo) {
        return channelInfoMapper.selectByUk(channelNo);
    }

    @CacheInvalidate(name = CacheTypeEnum.CHANNEL_INFO)
    public void deleteChannelInfo(Integer channelNo) {
        log.info("删除缓存{}", channelNo);
    }

    @Cached(name = CacheTypeEnum.CHANNEL_EXT_PARAM, cacheType = CacheType.LOCAL, expire = 36000, localLimit = 10000, cacheNullValue = true)
    public Map<String, Map<String, String>> getChannelExtParam(Integer channelNo) {
        ChannelExtParam query = new ChannelExtParam();
        query.setChannelNo(channelNo);
        List<ChannelExtParam> list = channelExtParamMapper.select(query);
        Map<String, Map<String, String>> channelParamMap = new ConcurrentHashMap<>(10);
        list.forEach(channelExtParam -> {
            if (channelParamMap.get(channelExtParam.getChannelMerchantNo()) == null) {
                Map<String, String> paramMap = new HashMap<>(10);
                paramMap.put(channelExtParam.getParamKey(), channelExtParam.getParamValue());
                channelParamMap.put(channelExtParam.getChannelMerchantNo(), paramMap);
            } else {
                channelParamMap.get(channelExtParam.getChannelMerchantNo()).put(channelExtParam.getParamKey(), channelExtParam.getParamValue());
            }
        });
        return channelParamMap;
    }

    @CacheInvalidate(name = CacheTypeEnum.CHANNEL_EXT_PARAM)
    public void deleteChannelExtParam(Integer channelNo) {
        log.info("删除缓存{}", channelNo);
    }
}
