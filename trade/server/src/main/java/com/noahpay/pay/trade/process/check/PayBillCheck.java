package com.noahpay.pay.trade.process.check;

import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.kalvan.web.util.DateUtil;
import com.noahpay.pay.commons.db.trade.mapper.PayBillMapper;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.cust.bean.req.MerchantCheckTransRequest;
import com.noahpay.pay.cust.bean.res.MerchantCheckTransResponse;
import com.noahpay.pay.cust.controller.MerchantController;
import com.noahpay.pay.trade.bean.req.TransRequest;
import com.noahpay.pay.trade.constant.TransReturnCode;
import com.noahpay.pay.trade.context.TradeContext;
import com.noahpay.pay.trade.context.TradeContextHolder;
import com.noahpay.pay.trade.process.template.BaseCheck;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenliang
 */
@Component
@Slf4j
public class PayBillCheck extends BaseCheck<TransRequest, PayBill> {
    /**
     * TODO 切換为微服务 IMerchant
     */
    @Resource
    MerchantController iMerchant;
    @Resource
    PayBillMapper payBillMapper;


    @Override
    public void checkParams(TransRequest request) {
        log.debug("参数检查");
        String orderDate = String.valueOf(request.getOrderDate());
        if (!DateUtil.getDateString().equals(orderDate)) {
            throw new BizException(TransReturnCode.PARAM_REGION_OVER.formatMessage("订单日期", "只能是当天"));
        }
    }

    @Override
    public void checkRepeat(TransRequest request) {
        log.debug("订单查重");
        int count;
        try {
            count = payBillMapper.queryCountByOrderId(request.getMerchantNo(),
                    request.getOrderDate(), request.getOrderId());
        } catch (Throwable t) {
            throw new BizException(TransReturnCode.ERROR, t);
        }
        if (count != 0) {
            throw new BizException(TransReturnCode.DATA_REPEAT.formatMessage(request.getOrderId()));
        }
    }

    @Override
    public void checkBusiness(PayBill bill) {
        log.debug("业务检查");
        TradeContext tradeContext = TradeContextHolder.getTradeContext();
        MerchantCheckTransRequest merchantCheckTransRequest = new MerchantCheckTransRequest();
        merchantCheckTransRequest.setTransType(bill.getTransType());
        merchantCheckTransRequest.setMerchantNo(bill.getMerchantNo());
        merchantCheckTransRequest.setAmount(bill.getAmount());
        //检查商户入网
        Response<MerchantCheckTransResponse> merchantCheckTransResponse = iMerchant.merchantCheckTrans(new Request<>().setData(merchantCheckTransRequest));
        if (!merchantCheckTransResponse.success()) {
            throw new BizException(merchantCheckTransResponse);
        }
        tradeContext.setCheckTransResponse(merchantCheckTransResponse.getData());
        if (StringUtils.isBlank(bill.getMerchantName())) {
            //更新商户信息
            bill.setMerchantName(merchantCheckTransResponse.getData().getMerchantName());
        }
    }
}
