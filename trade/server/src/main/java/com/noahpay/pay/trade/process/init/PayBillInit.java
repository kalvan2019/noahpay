package com.noahpay.pay.trade.process.init;

import com.alibaba.fastjson.JSON;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.trade.bean.req.MicroPayRequest;
import com.noahpay.pay.trade.bean.req.OrderRequest;
import com.noahpay.pay.trade.bean.req.UnifiedOrderRequest;
import com.noahpay.pay.trade.constant.*;
import com.noahpay.pay.trade.process.template.BaseInit;
import com.noahpay.pay.trade.util.TransIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author chenliang
 */
@Component
@Slf4j
public class PayBillInit extends BaseInit<OrderRequest, PayBill> {

    @Override
    public PayBill initBill(OrderRequest request) {
        log.debug("初始化订单");
        PayBill bill = new PayBill();
        //交易流水
        bill.setTransType(TransTypeEnum.REAL_TIME_COLLECTION.code);
        bill.setTransId(TransIdUtils.genTransId(request.getMerchantNo(), request.getOrderDate(), bill.getTransType()));
        bill.setState(TransStateEnum.WAIT.code);
        bill.setCheckState(CheckStateEnum.CHECK_WAIT.code);
        bill.setNotifyState(NotifyStateEnum.NOTIFY_WAIT.code);
        bill.setPayResultCode(TransReturnCode.PROCESS.getCode());
        bill.setPayResultNote(TransReturnCode.PROCESS.getMessage());
        //订单信息
        bill.setOrderId(request.getOrderId());
        bill.setOrderDate(request.getOrderDate());
        bill.setDescription(request.getDescription());
        bill.setAttach(request.getAttach());
        bill.setTimeStart(request.getTimeStart());
        bill.setTimeExpire(request.getTimeExpire());
        bill.setAmount(request.getAmount().getTotal());
        bill.setCurrency(request.getAmount().getCurrency());
        bill.setSceneInfo(JSON.toJSONString(request.getSceneInfo()));
        bill.setNotifyUrl(request.getNotifyUrl());
        //商户信息检查业务信息后补全更新
        bill.setMerchantNo(request.getMerchantNo());
        //检查业务时带回
        bill.setMerchantName("");
        //计费信息计费时更新
        bill.setMerchantFee(0L);
        bill.setConsumerFee(0L);
        if (request instanceof UnifiedOrderRequest) {
            initOrder(bill, (UnifiedOrderRequest) request);
        }
        if (request instanceof MicroPayRequest) {
            initMircroPay(bill, (MicroPayRequest) request);
        }
        return bill;
    }

    private void initOrder(PayBill bill, UnifiedOrderRequest request) {
        bill.setPayType(request.getPayType());
        bill.setPayerInfo(JSON.toJSONString(request.getPayerInfo()));
    }

    private void initMircroPay(PayBill bill, MicroPayRequest request) {
        bill.setPayType(PayTypeEnum.MICROPAY.code);
        bill.setChannelCodeUrl(request.getAuthCode());
    }
}
