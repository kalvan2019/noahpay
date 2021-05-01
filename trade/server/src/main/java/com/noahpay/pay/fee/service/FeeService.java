package com.noahpay.pay.fee.service;

import com.alibaba.fastjson.JSON;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.commons.db.cust.model.FeeMerchant;
import com.noahpay.pay.commons.db.cust.model.FeeRule;
import com.noahpay.pay.commons.db.cust.model.FeeSegmentRule;
import com.noahpay.pay.fee.bean.req.FeeCalculateRequest;
import com.noahpay.pay.fee.bean.res.FeeCalculateResponse;
import com.noahpay.pay.fee.constant.FeeObjectEnum;
import com.noahpay.pay.fee.constant.FeeReturnCode;
import com.noahpay.pay.fee.constant.FeeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Slf4j
@Service
public class FeeService {
    @Resource
    CacheFeeService cacheFeeService;

    public Response<FeeCalculateResponse> calculateFee(Request<FeeCalculateRequest> request) {
        FeeCalculateRequest feeCalculateRequest = request.getData();
        //获取计费模板
        FeeMerchant feeMerchant = cacheFeeService.getFeeMerchant(feeCalculateRequest.getMerchantNo(), feeCalculateRequest.getTransType());
        if (feeMerchant == null) {
            throw new BizException(FeeReturnCode.DATA_NOT_EXISTS.formatMessage("计费配置"));
        }
        log.debug("计费配置:{}", JSON.toJSONString(feeMerchant));
        //匹配计费规则
        List<FeeRule> feeRules = cacheFeeService.getFeeRule(feeMerchant.getFeeRule());
        if (feeRules.isEmpty()) {
            throw new BizException(FeeReturnCode.DATA_NOT_EXISTS.formatMessage("计费规则"));
        }
        Optional<FeeRule> feeRule = feeRules.stream().filter(feeRuleTemp -> {
            if (feeRuleTemp.getPayType().equals(feeCalculateRequest.getPayType())
                    || feeRuleTemp.getPayType().equals("0")) {
                if (feeRuleTemp.getChannelNo().equals(feeCalculateRequest.getChannelNo())
                        || feeRuleTemp.getChannelNo().equals(0)) {
                    if (feeRuleTemp.getBankType().equals(feeCalculateRequest.getBankType())
                            || feeRuleTemp.getBankType().equals("0")) {
                        return feeRuleTemp.getBankAccountType().equals(feeCalculateRequest.getBankAccountType())
                                || feeRuleTemp.getBankAccountType().equals(0);
                    }
                }
            }
            return false;
        }).findFirst();
        if (!feeRule.isPresent()) {
            throw new BizException(FeeReturnCode.DATA_NOT_EXISTS.formatMessage("计费规则"));
        }
        log.info("计费规则:{}", JSON.toJSONString(feeRule.get()));
        //商户手续费
        Long merchantFee = calculate(feeCalculateRequest.getAmount(), feeRule.get());
        Long minFee = feeRule.get().getMinFee();
        if (merchantFee < minFee) {
            //商户最低手续费
            merchantFee = minFee;
        }
        log.info("交易手续费:{}", merchantFee);
        if (feeCalculateRequest.getAmount() != null && feeCalculateRequest.getAmount() < merchantFee) {
            throw new BizException(FeeReturnCode.FAIL.formatMessage("手续费金额超过交易金额"));
        }
        //客户手续费
        long subMerchantFee = 0;
        if (feeCalculateRequest.getSubMerchantNo() != null && feeCalculateRequest.getSubMerchantFee() != null) {
            log.info("子商户手续费:{}", subMerchantFee);
            //目前支持平台外传入客户计费
            subMerchantFee = feeCalculateRequest.getSubMerchantFee();
            if (subMerchantFee < merchantFee) {
                throw new BizException(FeeReturnCode.FAIL.formatMessage("手续费发生倒挂"));
            }
        }
        //返回计费结果
        FeeCalculateResponse feeCalculateResponse = new FeeCalculateResponse();
        feeCalculateResponse.setFeeModel(feeMerchant.getFeeObject());
        feeCalculateResponse.setAmount(feeCalculateRequest.getAmount());
        //计费对象，商户、客户、持卡消费者
        if (feeMerchant.getFeeObject().equals(FeeObjectEnum.MERCHANT.code)) {
            feeCalculateResponse.setSubMerchantFee(subMerchantFee);
            feeCalculateResponse.setMerchantFee(merchantFee);
            feeCalculateResponse.setConsumerFee(0L);
        } else {
            feeCalculateResponse.setSubMerchantFee(0L);
            feeCalculateResponse.setMerchantFee(0L);
            feeCalculateResponse.setConsumerFee(merchantFee);
        }
        return Response.buildSuccess().setData(feeCalculateResponse);
    }

    private Long calculate(Long amount, FeeRule feeRule) {
        Long fee = null;
        if (feeRule.getFeeType().equals(FeeTypeEnum.FIXED.code)) {
            log.info("固定按笔计费");
            fee = feeRule.getFixFee();
        } else if (amount != null && feeRule.getFeeType().equals(FeeTypeEnum.RATE.code)) {
            log.info("固定费率计费");
            fee = getFee(amount, feeRule.getFeeRate());
        } else if (amount != null && feeRule.getFeeType().equals(FeeTypeEnum.SEGMENT.code)) {
            log.info("分段计费");
            List<FeeSegmentRule> feeSegmentRules = cacheFeeService.getFeeSegmentRule(feeRule.getFeeSegmentRule());
            if (feeSegmentRules.isEmpty()) {
                throw new BizException(FeeReturnCode.DATA_NOT_EXISTS.formatMessage("分段计费规则"));
            }
            List<FeeSegmentRule> result = feeSegmentRules.stream().filter(feeSegmentRule -> (
                    feeSegmentRule.getBeginAmount() < amount && amount <= feeSegmentRule.getEndAmount())
            ).collect(Collectors.toList());
            if (result.size() != 1) {
                throw new BizException(FeeReturnCode.FAIL.formatMessage("分段计费没有匹配到金额段"));
            }
            FeeSegmentRule feeSegmentRule = result.get(0);
            log.info("分段计费规则:{}", JSON.toJSONString(feeSegmentRule));
            if (feeSegmentRule.getFeeType().equals(FeeTypeEnum.FIXED.code)) {
                log.info("交易金额分段计费-固定按笔计费");
                fee = feeSegmentRule.getFixFee();
            } else if (feeSegmentRule.getFeeType().equals(FeeTypeEnum.RATE.code)) {
                log.info("交易金额分段计费-固定费率计费");
                fee = getFee(amount, feeSegmentRule.getFeeRate());
            }
        }
        if (fee == null) {
            throw new BizException(FeeReturnCode.FAIL.formatMessage("计费规则配置有误"));
        }
        return fee;
    }

    /**
     * 手续费计算方式（订单金额*费率）/单位
     *
     * @param amount 分
     * @param rate   n元/每万元
     * @return 手续费
     */
    private static Long getFee(Long amount, Long rate) {
        //订单金额分转成为元
        BigDecimal tempAmount = new BigDecimal(amount).divide(new BigDecimal(100));
        BigDecimal rateB = new BigDecimal(rate).divide(new BigDecimal(10000));
        //费率除以订单金额毫单位的
        BigDecimal fee = tempAmount.multiply(rateB).setScale(2, BigDecimal.ROUND_HALF_UP);
        //手续费元转分
        fee = fee.multiply(new BigDecimal(100));
        return fee.longValue();
    }
}
