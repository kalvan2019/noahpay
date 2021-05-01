package com.noahpay.pay.route.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.kalvan.client.constant.SwitchEnum;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Response;
import com.kalvan.web.util.DateUtil;
import com.noahpay.pay.commons.db.channel.mapper.ChannelMerchantPoolMapper;
import com.noahpay.pay.commons.db.channel.mapper.ChannelMultiMerchantMapper;
import com.noahpay.pay.commons.db.channel.model.*;
import com.noahpay.pay.route.bean.model.BankInfo;
import com.noahpay.pay.route.bean.req.RouteOrderRequest;
import com.noahpay.pay.route.bean.res.RouteResponse;
import com.noahpay.pay.route.constant.ChannelExtParamKey;
import com.noahpay.pay.route.constant.RouteReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Service
@Slf4j
public class RouteService {

    @Resource
    private ChannelMultiMerchantMapper channelMultiMerchantMapper;
    @Resource
    private ChannelMerchantPoolMapper channelMerchantPoolMapper;
    @Resource
    private CacheChannelService cacheChannelService;
    @Resource
    private CacheRouteService cacheRouteService;
    @Resource
    private ChannelService channelService;

    /**
     * 订单交易路由
     */
    public Response<RouteResponse> orderRoute(RouteOrderRequest request) {
        //1.指定通道商户号路由
        if (StringUtils.isNotBlank(request.getChannelMerchantNo())) {
            Integer channelNo = multiMerchantRoute(request.getMerchantNo(), request.getPayType(), request.getChannelMerchantNo());
            RouteResponse routeResponse = channelMerchantPoolRoute(channelNo, request.getChannelMerchantNo(), request);
            return buildRouteResponse(routeResponse);
        }
        //2.根据商户配置路由route_merchant(生效状态缓存)
        RouteMerchant routeMerchant = cacheRouteService.getRouteMerchant(request.getMerchantNo(), request.getPayType());
        if (routeMerchant == null) {
            throw new BizException(RouteReturnCode.CODE_6412.formatMessage("商户未配置路由规则"));
        }
        //2.1指定通道路由
        if (routeMerchant.getChannelNo() != 0) {
            RouteResponse routeResponse = channelMerchantPoolRoute(routeMerchant.getChannelNo(), null, request);
            return buildRouteResponse(routeResponse);
        }
        //2.2根据规则路由route_rule(生效状态缓存)
        List<RouteRule> cacheRouteRules = cacheRouteService.getRouteRule(routeMerchant.getRouteRule(), request.getBankAccountType());
        //不能改变cache对象的值
        List<RouteRule> newRouteRules = ObjectUtil.cloneByStream(cacheRouteRules);
        newRouteRules = merchantRoute(newRouteRules, request.getChannelNoList(), request.getOrderAmount(), request.getBankType());
        newRouteRules = bankRoute(newRouteRules, request.getPayType(), request.getOrderAmount(), request.getBankType(), request.getBankAccountType());
        //通道商户路由channel_merchant_pool
        RouteResponse routeResponse = channelMerchantPoolRoute(newRouteRules, request);
        return buildRouteResponse(routeResponse);
    }

    /**
     * 商户配置路由筛选
     * route_rule
     */
    private List<RouteRule> merchantRoute(List<RouteRule> routeRules, List<Integer> channelNoList, Long orderAmount, String bankType) {
        AtomicReference<String> errorMsg = new AtomicReference<>("");
        List<RouteRule> list = routeRules.stream().filter(routeRuleTemp -> {
            errorMsg.set("");
            if (channelNoList != null && !channelNoList.isEmpty() && !channelNoList.contains(routeRuleTemp.getChannelNo())) {
                errorMsg.set("未匹配到指定通道");
                return false;
            }
            if (StringUtils.isNotBlank(bankType) && !"0".equals(routeRuleTemp.getBankType()) && !routeRuleTemp.getBankType().equals(bankType)) {
                errorMsg.set("不支持该银行");
                return false;
            }
            //时间控制
            int currentDate = DateUtil.getDateInteger();
            if (currentDate < routeRuleTemp.getBeginDate()) {
                errorMsg.set("当前时间不支持该银行卡交易");
                return false;
            }
            if (currentDate > routeRuleTemp.getEndDate()) {
                errorMsg.set("当前时间不支持该银行卡交易");
                return false;
            }
            int currentTime = Integer.parseInt(DatePattern.PURE_TIME_FORMAT.format(DateUtil.date()));
            if (currentTime < Integer.parseInt(routeRuleTemp.getBeginTime())) {
                errorMsg.set("当前时间不支持该银行卡交易");
                return false;
            }
            if (currentTime > Integer.parseInt(routeRuleTemp.getEndTime())) {
                errorMsg.set("当前时间不支持该银行卡交易");
                return false;
            }
            //限额控制
            if (orderAmount != null) {
                if (orderAmount < routeRuleTemp.getLimitMinAmount()) {
                    errorMsg.set("商户单笔金额过低");
                    return false;
                }
                if (orderAmount > routeRuleTemp.getLimitMaxAmount()) {
                    errorMsg.set("商户单笔金额超限");
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
        if (list.isEmpty()) {
            throw new BizException(RouteReturnCode.CODE_6412.formatMessage(errorMsg.get()));
        }
        return list;
    }

    /**
     * 通道银行路由筛选
     * channel_support_pay_type+channel_support_bank+channel_support_bank_type_group
     */
    private List<RouteRule> bankRoute(List<RouteRule> routeRules, String payType, Long orderAmount, String bankType, Integer bankAccontType) {
        AtomicReference<String> errorMsg = new AtomicReference<>("");
        List<RouteRule> list = routeRules.stream().filter(routeRuleTemp -> {
            routeRuleTemp.setBankInfos(new ArrayList<>());
            ChannelSupportPayType channelSupportPayType = cacheRouteService.getChannelSupportPayType(routeRuleTemp.getChannelNo(), payType);
            errorMsg.set("");
            if (channelSupportPayType == null) {
                errorMsg.set("通道不支持该支付方式");
                return false;
            }
            if (orderAmount != null) {
                if (orderAmount + channelSupportPayType.getDayUseAmount() > channelSupportPayType.getDayLimitAmount()) {
                    errorMsg.set("日交易额超限");
                    return false;
                }
                if (orderAmount + channelSupportPayType.getMonthUseAmount() > channelSupportPayType.getMonthLimitAmount()) {
                    errorMsg.set("月交易额超限");
                    return false;
                }
                if (1 + channelSupportPayType.getDayUseNumber() > channelSupportPayType.getDayLimitNumber()) {
                    errorMsg.set("日交易笔数超限");
                    return false;
                }
            }
            //TODO 节假日路由
            //TODO city路由
            //银行组配置
            if (SwitchEnum.OPEN.code == channelSupportPayType.getBankRouteEnabled()) {
                List<ChannelSupportBank> channelSupportBanks = cacheRouteService.getChannelSupportBanks(routeRuleTemp.getChannelNo(), payType);
                List<ChannelSupportBank> routeChannelSupportBanks = channelSupportBanks.stream().filter(channelSupportBankTemp -> {
                    errorMsg.set("");
                    if (bankAccontType != null && bankAccontType != 0) {
                        if (channelSupportBankTemp.getBankAccountType() != 0 && !channelSupportBankTemp.getBankAccountType().equals(bankAccontType)) {
                            errorMsg.set("银行账户类型未匹配到路由");
                            return false;
                        }
                    }
                    if (StringUtils.isNotBlank(bankType)) {
                        if (channelSupportBankTemp.getBankSupport() == SwitchEnum.OPEN.code && !channelSupportBankTemp.getBankType().equals(bankType)) {
                            errorMsg.set("不支持该银行");
                            return false;
                        }
                        if (channelSupportBankTemp.getBankSupport() == SwitchEnum.CLOSE.code && channelSupportBankTemp.getBankType().equals(bankType)) {
                            errorMsg.set("不支持该银行");
                            return false;
                        }
                    }
                    int currentTime = Integer.parseInt(DatePattern.PURE_TIME_FORMAT.format(DateUtil.date()));
                    if (StringUtils.isNotBlank(channelSupportBankTemp.getBeginTime()) && currentTime < Integer.parseInt(channelSupportBankTemp.getBeginTime())) {
                        errorMsg.set("当前时间不支持该银行卡交易");
                        return false;
                    }
                    if (StringUtils.isNotBlank(channelSupportBankTemp.getEndTime()) && currentTime > Integer.parseInt(channelSupportBankTemp.getEndTime())) {
                        errorMsg.set("当前时间不支持该银行卡交易");
                        return false;
                    }
                    if (orderAmount != null) {
                        if (orderAmount < channelSupportBankTemp.getLimitMinAmount()) {
                            errorMsg.set("银行单笔金额过低");
                            return false;
                        }
                        if (orderAmount > channelSupportBankTemp.getLimitMaxAmount()) {
                            errorMsg.set("银行单笔金额超限");
                            return false;
                        }
                    }
                    //银行组列表
                    BankInfo bankInfo = new BankInfo();
                    bankInfo.setBankType(channelSupportBankTemp.getBankType());
                    bankInfo.setBankAccountType(channelSupportBankTemp.getBankAccountType());
                    routeRuleTemp.getBankInfos().add(bankInfo);
                    return true;
                }).collect(Collectors.toList());
                return routeChannelSupportBanks.size() > 0;
            }
            //单银行配置
            BankInfo bankInfo = new BankInfo();
            bankInfo.setBankType(routeRuleTemp.getBankType());
            bankInfo.setBankAccountType(routeRuleTemp.getBankAccountType());
            routeRuleTemp.getBankInfos().add(bankInfo);
            return true;
        }).collect(Collectors.toList());
        if (list.isEmpty()) {
            throw new BizException(RouteReturnCode.CODE_6412.formatMessage(errorMsg.get()));
        }
        return list;
    }

    /**
     * 商户指定通道商户路由
     * channel_multi_merchant
     */
    private Integer multiMerchantRoute(Long merchantNo, String payType, String channelMerchantNo) {
        log.debug("指定通道商户路由：{}", channelMerchantNo);
        ChannelMultiMerchant channelMultiMerchant = channelMultiMerchantMapper.queryChannelMultiMerchant(merchantNo, payType, channelMerchantNo);
        if (channelMultiMerchant == null) {
            throw new BizException(RouteReturnCode.CODE_6412.formatMessage("指定通道商户号未配置"));
        }
        Integer channelNo = channelMultiMerchant.getChannelNo();
        ChannelInfo channelInfo = cacheChannelService.getChannelInfo(channelNo);
        if (channelInfo == null || SwitchEnum.OPEN.code != channelInfo.getState()) {
            throw new BizException(RouteReturnCode.CHANNEL_CLOSE.formatMessage(channelNo));
        }
        if (SwitchEnum.OPEN.code != channelInfo.getMultiMerchantConvert()) {
            throw new BizException(RouteReturnCode.CODE_6412.formatMessage("不支持指定通道商户号"));
        }
        return channelNo;
    }

    /**
     * 通道商户路由
     * channel_merchant_pool
     */
    private RouteResponse channelMerchantPoolRoute(List<RouteRule> routeRules, RouteOrderRequest request) {
        if (request.isCashierRoute()) {
            //TODO 收银台路由直接返回列表
            RouteResponse routeResponse = new RouteResponse();
            routeResponse.setChannelNo(routeRules.get(0).getChannelNo());
            routeResponse.setBankInfos(new ArrayList<>());
            routeRules.stream().forEach(routeRuleTemp -> {
                routeResponse.getBankInfos().addAll(routeRuleTemp.getBankInfos());
            });
            return routeResponse;
        }
        List<RouteResponse> routeResponses = routeRules.stream().map(routeRuleTemp -> {
            try {
                RouteResponse routeResponse = channelMerchantPoolRoute(routeRuleTemp.getChannelNo(), null, request);
                routeResponse.setWeight(routeRuleTemp.getWeight());
                routeResponse.setPriority(routeRuleTemp.getPriority());
                return routeResponse;
            } catch (BizException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (routeResponses.isEmpty()) {
            throw new BizException(RouteReturnCode.CODE_6412);
        }
        if (routeResponses.size() == 1) {
            return routeResponses.get(0);
        }
        //TODO 未测试 优先级筛选(值越小优先级越高)
        AtomicReference<Integer> minPriority = new AtomicReference<>(routeResponses.get(0).getPriority());
        AtomicInteger sumWeight = new AtomicInteger();
        List<int[]> regions = new ArrayList<>();
        routeResponses = routeResponses.stream().filter(routeResponseTemp -> {
            if (routeResponseTemp.getPriority() < minPriority.get()) {
                minPriority.set(routeResponseTemp.getPriority());
                return true;
            }
            if (routeResponseTemp.getPriority().equals(minPriority.get())) {
                int[] region = new int[2];
                int begin = sumWeight.get();
                int end = sumWeight.get() + routeResponseTemp.getWeight();
                region[0] = begin;
                region[1] = end;
                regions.add(region);
                sumWeight.set(end);
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        if (routeResponses.size() == 1) {
            return routeResponses.get(0);
        }
        //权重筛选(同一优先级下启用)
        int random = RandomUtil.randomInt(sumWeight.get());
        log.info("权重因子:{},掷筛子:{}", sumWeight, random);
        for (int i = 0; i < regions.size(); i++) {
            if (regions.get(i)[0] <= random && random <= regions.get(i)[1]) {
                return routeResponses.get(i);
            }
        }
        throw new BizException(RouteReturnCode.CODE_6412);
    }

    /**
     * 通道商户路由
     * channel_merchant_pool
     */
    private RouteResponse channelMerchantPoolRoute(Integer channelNo, String channelMerchantNo, RouteOrderRequest request) {
        log.info("指定通道路由:{}", channelNo);
        if (request.getChannelNoList() != null && !request.getChannelNoList().isEmpty() && !request.getChannelNoList().contains(channelNo)) {
            throw new BizException(RouteReturnCode.CODE_6412.formatMessage("未匹配到指定通道"));
        }
        ChannelInfo channelInfo = cacheChannelService.getChannelInfo(channelNo);
        if (channelInfo == null || channelInfo.getState() != SwitchEnum.OPEN.code) {
            throw new BizException(RouteReturnCode.CHANNEL_CLOSE.formatMessage(channelNo));
        }
        if (request.isCashierRoute()) {
            //TODO 收银台路由直接返回列表
            RouteResponse routeResponse = new RouteResponse();
            return routeResponse;
        }
        if (SwitchEnum.OPEN.code != channelInfo.getMerchantPoolConvert()) {
            RouteResponse routeResponse = new RouteResponse();
            routeResponse.setChannelNo(channelNo);
            routeResponse.setChannelMerchantNo(channelMerchantNo);
            return routeResponse;
        }
        //筛选出需要商户池处理通道
        List<ChannelMerchantPool> channelMerchantPools;
        List<Integer> channelNoList = new ArrayList<>();
        channelNoList.add(channelNo);
        //TODO 需要城市和行业路由时开启
//        if (request.getCity() != null) {
//            //精确到城市码与MCC
//            if (request.getMcc() != null) {
//                channelMerchantPools = channelMerchantPoolMapper.queryChannelMerchantPool(channelNoList, channelMerchantNo, request.getOrderAmount(), request.getCity(), request.getMcc(), request.getMerchantNo());
//            }
//            if (channelMerchantPools == null || channelMerchantPools.isEmpty()) {
//                channelMerchantPools = channelMerchantPoolMapper.queryChannelMerchantPool(channelNoList, channelMerchantNo, request.getOrderAmount(), request.getCity(), null, request.getMerchantNo());
//            }
//        }
        // 商户池查找匹配商户查找得到子商户号
        channelMerchantPools = channelMerchantPoolMapper.queryChannelMerchantPool(channelNoList, channelMerchantNo, request.getOrderAmount(), null, null, request.getMerchantNo());
        if (channelMerchantPools == null || channelMerchantPools.isEmpty()) {
            throw new BizException(RouteReturnCode.CODE_6412.formatMessage("无可用商户"));
        }
        //TODO 随机或轮询算法 redisUtil.getPoolIndex(channelMerchantPools.size());
        int index = RandomUtil.randomInt(channelMerchantPools.size());
        ChannelMerchantPool channelMerchantPool = channelMerchantPools.get(index);
        RouteResponse routeResponse = new RouteResponse();
        BeanUtils.copyProperties(channelMerchantPool, routeResponse);
        return routeResponse;
    }

    private Response<RouteResponse> buildRouteResponse(RouteResponse routeResponse) {
        if (routeResponse == null) {
            throw new BizException(RouteReturnCode.CODE_6412);
        }
        log.info("路由选中通道:{}", routeResponse.getChannelNo());
        ChannelInfo channelInfo = cacheChannelService.getChannelInfo(routeResponse.getChannelNo());
        if (StringUtils.isBlank(routeResponse.getChannelMerchantNo())) {
            routeResponse.setChannelMerchantNo(channelInfo.getChannelMerchantNo());
        }
        if (StringUtils.isBlank(routeResponse.getChannelMerchantName())) {
            routeResponse.setChannelMerchantName(channelService.getChannelExtParamValue(routeResponse.getChannelNo(), routeResponse.getChannelMerchantNo(), ChannelExtParamKey.MERCHANT_NAME.code));
        }
        return Response.buildSuccess().setData(routeResponse);
    }
}
