package com.noahpay.pay.risk.service;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Response;
import com.noahpay.pay.commons.db.risk.mapper.MerchantTransSumMapper;
import com.noahpay.pay.commons.db.risk.model.MerchantTransSum;
import com.noahpay.pay.risk.bean.req.MerchantUseRequest;
import com.noahpay.pay.risk.bean.req.RiskTransRequest;
import com.noahpay.pay.risk.bean.res.RiskResponse;
import com.noahpay.pay.risk.constant.RiskLevelEnum;
import com.noahpay.pay.risk.constant.RiskReturnCode;
import com.kalvan.web.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Service
@Slf4j
public class RiskTransService {
    private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(5, 15, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
    @Resource
    MerchantTransSumMapper merchantTransSumMapper;
    @Resource
    RiskBlackService riskBlackService;
    @Resource
    CacheRiskService cacheRiskService;

    public Response<RiskResponse> checkRiskTrade(RiskTransRequest request) {
        if (!request.getTransDate().equals(DateUtil.getDateInteger())) {
            throw new BizException(RiskReturnCode.PARAM_ILLEGAL);
        }
        //黑名单检查
        try {
            Response<RiskResponse> response = riskBlackService.checkBlack(request);
            if (response.success() && RiskLevelEnum.RISK_LEVEL_ZERO.code == response.getData().getRiskLevel()) {
                return response;
            }
        } catch (Throwable e) {
            log.error("黑名单风控检查异常,不对交易进行阻断", e);
        }
        //限额检查
        RiskResponse riskResponse = new RiskResponse();
        riskResponse.setRiskLevel(RiskLevelEnum.RISK_LEVEL_ONE.code);
        riskResponse.setRefTransId(request.getRefTransId());
        Response<RiskResponse> response = Response.buildSuccess().setData(riskResponse);
        //保存风控参数
        JSONObject jsonParams = new JSONObject();
        //保存风控原因
        String riskReason = "风控异常";
        try {
            MerchantTransSum merchantTransSum = cacheRiskService.getMerchantTradeSum(request.getMerchantNo(), request.getTransType());
            if (null == merchantTransSum) {
                //这里会抛出异常，并发存在后插入会失败
                merchantTransSum = new MerchantTransSum();
                merchantTransSum.setTransDate(request.getTransDate());
                merchantTransSum.setMerchantNo(request.getMerchantNo());
                merchantTransSum.setTransType(request.getTransType());
                //初始化当天累计金额笔数
                merchantTransSum.setDayUseNumber(0);
                merchantTransSum.setDayUseAmount(0L);
                merchantTransSum.setMonthUseAmount(0L);
                merchantTransSum.setMonthUseNumber(0);
                merchantTransSumMapper.insertSelective(merchantTransSum);
            } else {
                //记录不为空时，判断是否跨月，会存在误差，忽略
                String mon = merchantTransSum.getTransDate().toString().substring(0, 6);
                String monTrade = request.getTransDate().toString().substring(0, 6);
                if (!mon.equals(monTrade)) {
                    //不同月
                    merchantTransSum.setTransDate(request.getTransDate());
                    merchantTransSum.setDayUseNumber(0);
                    merchantTransSum.setDayUseAmount(0L);
                    merchantTransSum.setMonthUseAmount(0L);
                    merchantTransSum.setMonthUseNumber(0);
                    merchantTransSumMapper.updateByUkSelective(merchantTransSum);
                } else if (!merchantTransSum.getTransDate().equals(request.getTransDate())) {
                    //不同日
                    merchantTransSum.setTransDate(request.getTransDate());
                    merchantTransSum.setDayUseNumber(0);
                    merchantTransSum.setDayUseAmount(0L);
                    merchantTransSumMapper.updateByUkSelective(merchantTransSum);
                }
            }
            if (merchantTransSum.getDayUseAmount() + request.getTradeAmount() > request.getDayUseLimitAmount()) {
                //超过日限额
                jsonParams.put("dayUseLimitAmount", NumberUtil.roundStr(request.getDayUseLimitAmount().doubleValue() / 100, 2) + "元");
                riskReason = RiskReturnCode.CODE_5421.getMessage();
                riskResponse.setRiskLevel(RiskLevelEnum.RISK_LEVEL_ZERO.code);
                return response;
            }
            if (merchantTransSum.getDayUseNumber() + 1 > request.getDayUseLimitNumber()) {
                //超过日笔数
                jsonParams.put("dayUseLimitNumber", request.getDayUseLimitNumber());
                riskReason = RiskReturnCode.CODE_5420.getMessage();
                riskResponse.setRiskLevel(RiskLevelEnum.RISK_LEVEL_ZERO.code);
                return response;
            }
            if (merchantTransSum.getMonthUseAmount() + request.getTradeAmount() > request.getMonthUseLimitAmount()) {
                //超过月限额
                jsonParams.put("monthUseLimitAmount", NumberUtil.roundStr(request.getMonthUseLimitAmount().doubleValue() / 100, 2) + "元");
                riskReason = RiskReturnCode.CODE_5423.getMessage();
                riskResponse.setRiskLevel(RiskLevelEnum.RISK_LEVEL_ZERO.code);
                return response;
            }
            if (merchantTransSum.getMonthUseNumber() + 1 > request.getMonthUseLimitNumber()) {
                //超过月笔数
                jsonParams.put("monthUseLimitNumber", request.getMonthUseLimitNumber());
                riskReason = RiskReturnCode.CODE_5422.getMessage();
                riskResponse.setRiskLevel(RiskLevelEnum.RISK_LEVEL_ZERO.code);
                return response;
            }
        } catch (Throwable e) {
            log.error("商户交易累计风控检查异常,不对交易进行阻断", e);
        } finally {
            riskResponse.setRiskParams(jsonParams.toJSONString());
            riskResponse.setRiskReason(riskResponse.getRiskLevel() == RiskLevelEnum.RISK_LEVEL_ONE.code ? "通过风控" : riskReason);
            //非黑名单风控和 风控等级为监控、阻断的则记录。
            if (RiskLevelEnum.RISK_LEVEL_ZERO.code == riskResponse.getRiskLevel()
                    || RiskLevelEnum.RISK_LEVEL_TWO.code == riskResponse.getRiskLevel()) {
//                RiskList riskList = new RiskList();
//                riskList.setRefTransId(request.getRefTransId());
//                riskList.setRiskLevel(riskTradeResponse.getRiskLevel());
//                riskList.setRiskParams(riskTradeResponse.getRiskParams());
//                riskList.setRiskReason(riskTradeResponse.getRiskReason());
//                riskListMapper.insertSelective(riskList);
                log.info("记录风险事件记录完成");
            }
        }
        return response;
    }

    /**
     * 交易额度累计处理
     */
    public void updateMerchantDayUseAmount(MerchantUseRequest request) {
        log.info("开始更新交易累计{},数量{}", request.getTransType(), request.getAmountMap().size());
        try {
            for (Map.Entry<Long, Long> entry : request.getAmountMap().entrySet()) {
                Long number = request.getNumberMap().get(entry.getKey());
                if (number == null) {
                    log.error("信息不合{}", entry.getKey());
                    number = 1L;
                }
                Long finalNumber = number;
                THREAD_POOL.execute(() -> merchantTransSumMapper.updateTransSum(entry.getKey(), request.getTransType(), entry.getValue(), finalNumber));
            }
        } catch (Throwable e) {
            log.error("updateMerchantDayUseAmount处理异常", e);
        }
    }
}