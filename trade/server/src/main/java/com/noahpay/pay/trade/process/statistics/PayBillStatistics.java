package com.noahpay.pay.trade.process.statistics;

import com.kalvan.client.model.Request;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.trade.process.template.BaseStatistics;
import com.noahpay.pay.risk.bean.req.MerchantUseRequest;
import com.noahpay.pay.risk.iface.IRiskDataHandle;
import com.noahpay.pay.route.bean.req.ChannelUseRequest;
import com.noahpay.pay.route.iface.IRouteDataHandle;
import com.noahpay.pay.trade.constant.TransStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenliang
 */
@Component
@Slf4j
public class PayBillStatistics extends BaseStatistics<PayBill> {
    @Resource
    IRiskDataHandle iRiskDataHandle;

    @Resource
    IRouteDataHandle iRouteDataHandle;
    Long calcMerchantUseCurrentTime = System.currentTimeMillis();
    /**
     * 商户号、使用金额
     */
    Map<Long, Long> merUseAmountMap = new ConcurrentHashMap<>();
    /**
     * 商户号、使用笔数
     */
    Map<Long, Long> merUseNumberMap = new ConcurrentHashMap<>();
    Long calcChannelUseCurrentTime = System.currentTimeMillis();
    /**
     * 通道号、商户号、子商户号、使用金额
     */
    Map<Integer, Map<String, Map<String, Long>>> chMerDayUseAmountMap = new HashMap<>();
    /**
     * 支付类型、通道号、使用次数
     */
    Map<String, Map<Integer, Long>> chDayUseNumberMap = new HashMap<>();
    /**
     * 支付类型、通道号、使用额
     */
    Map<String, Map<Integer, Long>> chDayUseAmountMap = new HashMap<>();

    @Override
    public synchronized void successCount(PayBill bill) {
        log.debug("商户交易成功累计处理");
        try {
            if (bill.getState() == TransStateEnum.SUCCESS_PROCESS.code) {
                calcMerchantUse(bill);
                calcChannelUse(bill);
            }
            saveMerchantUse(bill.getTransType());
            saveChannelUse();
        } catch (Throwable t) {
            //统计不能抛出异常影响结果
            log.error("处理异常", t);
        }
    }

    @Override
    public void undoCount(PayBill bill) {
        try {
            if (bill.getState() == TransStateEnum.FAIL.code) {
                calcMerchantUse(bill);
            }
            saveMerchantUse(bill.getTransType());
        } catch (Throwable t) {
            log.error("交易额度冲正异常", t);
        }
    }

    /**
     * 计算额度,成功+，失败-
     *
     * @param bill 流水
     */
    private void calcMerchantUse(PayBill bill) {
        if (bill.getAmount() == null) {
            return;
        }
        Long tempAmount = bill.getAmount();
        long tempNumber = 1L;
        if (bill.getState() == TransStateEnum.FAIL.code) {
            //失败需要充回
            tempAmount = -tempAmount;
            tempNumber = -1L;
        }
        Long dayUseAmount = merUseAmountMap.get(bill.getMerchantNo());
        //商户使用额累计
        if (dayUseAmount == null) {
            dayUseAmount = tempAmount;
        } else {
            dayUseAmount = dayUseAmount + tempAmount;
        }
        merUseAmountMap.put(bill.getMerchantNo(), dayUseAmount);
        //商户使用笔累计
        Long dayUseNumber = merUseNumberMap.get(bill.getMerchantNo());
        if (dayUseNumber == null) {
            dayUseNumber = tempNumber;
        } else {
            dayUseNumber = dayUseNumber + tempNumber;
        }
        merUseNumberMap.put(bill.getMerchantNo(), dayUseNumber);
    }

    private void saveMerchantUse(Integer transType) {
        //超过4S
        if (System.currentTimeMillis() - calcMerchantUseCurrentTime >= 4000 && !merUseAmountMap.isEmpty()) {
            MerchantUseRequest request = new MerchantUseRequest();
            request.setTransType(transType);
            request.setAmountMap(merUseAmountMap);
            request.setNumberMap(merUseNumberMap);
            iRiskDataHandle.updateMerchantUse(new Request<>().setData(request));
            calcMerchantUseCurrentTime = System.currentTimeMillis();
            merUseAmountMap.clear();
            merUseNumberMap.clear();
        }
    }

    private void calcChannelUse(PayBill bill) {
        if (bill.getChannelNo() == null || bill.getChannelAmount() == null) {
            return;
        }
        //默认从头开始循环
        Long merDayUseAmount = bill.getChannelAmount();
        long dayUseNumber = 1L;
        Long dayUseAmount = bill.getChannelAmount();
        //子商户使用额累计
        Map<String, Map<String, Long>> merPoolMap = chMerDayUseAmountMap.get(bill.getChannelNo());
        String channelSubMerchantNo = StringUtils.isBlank(bill.getChannelSubMerchantNo()) ? "*" : bill.getChannelSubMerchantNo();
        if (merPoolMap != null) {
            if (merPoolMap.get(bill.getChannelMerchantNo()) != null) {
                Long incrAmount = merPoolMap.get(bill.getChannelMerchantNo()).get(channelSubMerchantNo);
                if (incrAmount != null) {
                    merDayUseAmount = bill.getChannelAmount() + incrAmount;
                }
                merPoolMap.get(bill.getChannelMerchantNo()).put(channelSubMerchantNo, merDayUseAmount);
            } else {
                Map<String, Long> newMap = new HashMap<>();
                newMap.put(channelSubMerchantNo, merDayUseAmount);
                merPoolMap.put(bill.getChannelMerchantNo(), newMap);
            }
        } else {
            Map<String, Map<String, Long>> poolMap = new HashMap<>();
            Map<String, Long> newMap = new HashMap<>();
            newMap.put(channelSubMerchantNo, merDayUseAmount);
            poolMap.put(bill.getChannelMerchantNo(), newMap);
            chMerDayUseAmountMap.put(bill.getChannelNo(), poolMap);
        }
        //消费笔数统计
        if (chDayUseNumberMap.get(bill.getPayType()) != null) {
            //payType在交易系统处理到此步骤不为空
            Long incrNumber = chDayUseNumberMap.get(bill.getPayType()).get(bill.getChannelNo());
            if (incrNumber != null) {
                dayUseNumber = incrNumber + 1;
            }
            chDayUseNumberMap.get(bill.getPayType()).put(bill.getChannelNo(), dayUseNumber);
        } else {
            Map<Integer, Long> newMap = new HashMap<>();
            newMap.put(bill.getChannelNo(), dayUseNumber);
            chDayUseNumberMap.put(bill.getPayType(), newMap);
        }
        //消费使用额统计
        if (chDayUseAmountMap.get(bill.getPayType()) != null) {
            Long incrAmount = chDayUseAmountMap.get(bill.getPayType()).get(bill.getChannelNo());
            if (incrAmount != null) {
                dayUseAmount = incrAmount + bill.getChannelAmount();
            }
            chDayUseAmountMap.get(bill.getPayType()).put(bill.getChannelNo(), dayUseAmount);
        } else {
            Map<Integer, Long> newMap = new HashMap<>();
            newMap.put(bill.getChannelNo(), dayUseAmount);
            chDayUseAmountMap.put(bill.getPayType(), newMap);
        }
    }

    private void saveChannelUse() {
        //超过3S
        if (System.currentTimeMillis() - calcChannelUseCurrentTime >= 3000) {
            ChannelUseRequest request = new ChannelUseRequest();
            request.setChannelMerchantUseAmount(chMerDayUseAmountMap);
            request.setChannelUseAmount(chDayUseAmountMap);
            request.setChannelUseNumber(chDayUseNumberMap);
            iRouteDataHandle.updateChannelUse(new Request<>().setData(request));
            //清空批次
            chMerDayUseAmountMap.clear();
            chDayUseNumberMap.clear();
            chDayUseAmountMap.clear();
            calcChannelUseCurrentTime = System.currentTimeMillis();
        }
    }
}
