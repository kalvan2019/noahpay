package com.noahpay.pay.trade.process.risk;

import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.kalvan.web.util.DateUtil;
import com.noahpay.pay.risk.controller.RiskController;
import com.noahpay.pay.trade.context.TradeContext;
import com.noahpay.pay.trade.context.TradeContextHolder;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.trade.process.template.BaseRisk;
import com.noahpay.pay.risk.bean.req.RiskTransRequest;
import com.noahpay.pay.risk.bean.res.RiskResponse;
import com.noahpay.pay.risk.constant.RiskAmountFlagEnum;
import com.noahpay.pay.risk.constant.RiskLevelEnum;
import com.noahpay.pay.trade.constant.TransReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenliang
 */
@Component
@Slf4j
public class PayBillRisk extends BaseRisk<PayBill> {
    /**
     * TODO 切換为微服务 IRisk
     */
    @Resource
    RiskController iRisk;

    @Override
    public void checkRisk(PayBill bill) {
        log.debug("风控检查");
        TradeContext tradeContext = TradeContextHolder.getTradeContext();
        RiskTransRequest request = new RiskTransRequest();
        request.setRefTransId(bill.getTransId());
        request.setTradeAmount(bill.getAmount());
        request.setTransDate(DateUtil.getDateInteger());
        request.setMerchantNo(bill.getMerchantNo());
        request.setAmountFlag(RiskAmountFlagEnum.AMOUNT_IN_FLAG.code);
        request.setTransType(bill.getTransType());
        request.setDayUseLimitNumber(tradeContext.getCheckTransResponse().getDayLimitNumber());
        request.setDayUseLimitAmount(tradeContext.getCheckTransResponse().getDayLimitAmount());
        request.setMonthUseLimitNumber(tradeContext.getCheckTransResponse().getMonthLimitNumber());
        request.setMonthUseLimitAmount(tradeContext.getCheckTransResponse().getMonthLimitAmount());
        Response<RiskResponse> response = iRisk.checkTrans(new Request<>().setData(request));
        if (!response.success()) {
            throw new BizException(response);
        }
        RiskResponse data = response.getData();
        if (RiskLevelEnum.RISK_LEVEL_ZERO.code == data.getRiskLevel()) {
            bill.setPayResultCode(TransReturnCode.CODE_2404.getCode());
            bill.setPayResultNote(data.getRiskReason());
            throw new BizException(TransReturnCode.CODE_2404.formatMessage(data.getRiskReason()));
        }
    }
}
