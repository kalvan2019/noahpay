package com.noahpay.pay.trade.process.fee;

import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.fee.bean.req.FeeCalculateRequest;
import com.noahpay.pay.fee.bean.res.FeeCalculateResponse;
import com.noahpay.pay.fee.controller.FeeController;
import com.noahpay.pay.trade.constant.TransReturnCode;
import com.noahpay.pay.trade.context.TradeContext;
import com.noahpay.pay.trade.context.TradeContextHolder;
import com.noahpay.pay.trade.process.template.BaseFee;
import com.noahpay.pay.trade.service.PayBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenliang
 */
@Component
@Slf4j
public class PayBillFee extends BaseFee<PayBill> {
    /**
     * TODO 切換为微服务 IFee
     */
    @Resource
    FeeController iFee;
    @Resource
    PayBillService quickService;

    @Override
    public void calcFee(PayBill bill) {
        log.debug("计费");
        TradeContext tradeContext = TradeContextHolder.getTradeContext();
        FeeCalculateRequest feeCalculateRequest = new FeeCalculateRequest();
        feeCalculateRequest.setTransType(bill.getTransType());
        feeCalculateRequest.setMerchantNo(bill.getMerchantNo());
        //支付明细的payType为准
        feeCalculateRequest.setPayType(bill.getPayType());
        feeCalculateRequest.setAmount(bill.getAmount());
        feeCalculateRequest.setChannelNo(bill.getChannelNo());
        Response<FeeCalculateResponse> response = iFee.feeCalculate(new Request<>().setData(feeCalculateRequest));
        if (!response.success()) {
            throw new BizException(response);
        }
        FeeCalculateResponse feeCalculateResponse = response.getData();
        if (!bill.getAmount().equals(feeCalculateResponse.getAmount())) {
            throw new BizException(TransReturnCode.FAIL.formatMessage("计费返回金额不匹配"));
        }
        //TODO 手续费可能不同支付方式不一样因为先存到支付明细完成后再同步到bill表
        bill.setMerchantFee(feeCalculateResponse.getMerchantFee());
        bill.setConsumerFee(feeCalculateResponse.getConsumerFee());
        bill.setChannelAmount(bill.getAmount() + bill.getConsumerFee());

        // 计算手续费(更新 db),提高性能可以省略该步骤
        int updateFeeRow = quickService.updateFee(bill);
        if (updateFeeRow != 1) {
            throw new BizException(TransReturnCode.DATA_STATE_ERROR.formatMessage(bill.getOrderId()));
        }
    }
}
