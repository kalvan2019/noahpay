package com.noahpay.pay.trade.service;

import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Response;
import com.kalvan.web.util.DateUtil;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.enums.mq.msg.TransNotify;
import com.noahpay.pay.trade.bean.req.OrderRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.constant.NotifyStateEnum;
import com.noahpay.pay.trade.constant.PayTypeEnum;
import com.noahpay.pay.trade.constant.TransReturnCode;
import com.noahpay.pay.trade.constant.TransStateEnum;
import com.noahpay.pay.trade.context.TradeContextHolder;
import com.noahpay.pay.trade.event.MqOutput;
import com.noahpay.pay.trade.process.check.PayBillCheck;
import com.noahpay.pay.trade.process.fee.PayBillFee;
import com.noahpay.pay.trade.process.init.PayBillInit;
import com.noahpay.pay.trade.process.risk.PayBillRisk;
import com.noahpay.pay.trade.process.route.PayBillRoute;
import com.noahpay.pay.trade.process.trans.ChannelTransFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Service实现类
 *
 * @author chenliang
 */
@Slf4j
@Service
public class TransService {
    @Resource
    PayBillCheck payBillCheck;
    @Resource
    PayBillInit payBillInit;
    @Resource
    PayBillFee payBillFee;
    @Resource
    PayBillRisk payBillRisk;
    @Resource
    PayBillRoute payBillRoute;
    @Resource
    PayBillService payBillService;
    @Resource
    ChannelTransFactory channelTransFactory;
    @Resource
    MqOutput mqOutput;

    /**
     * 交易处理
     *
     * @param request 请求参数
     * @return 交易结果
     */
    public Response order(OrderRequest request) {
        payBillCheck.checkParams(request);
        payBillCheck.checkRepeat(request);
        PayBill payBill = payBillInit.initBill(request);
        payBillService.save(payBill);
        return syncProcess(payBill);
    }

    /**
     * 交易同步处理
     *
     * @param payBill 流水
     * @return Response
     */
    public Response syncProcess(PayBill payBill) {
        try {
            payBillCheck.checkBusiness(payBill);
            payBillRisk.checkRisk(payBill);
            payBillRoute.route(payBill);
            payBillFee.calcFee(payBill);
            channelTransFactory.getChannelTrans(payBill).channelConvertParam(payBill);
        } catch (BizException e) {
            if (!TransReturnCode.DATA_STATE_ERROR.getCode().equals(e.getCode())) {
                payBill.setPayResultCode(e.getCode());
                payBill.setPayResultNote(e.getMessage());
                payBillService.updateFail(payBill);
                notify(payBill);
            }
            return getResult(payBill);
        } catch (Throwable t) {
            log.error("处理异常", t);
            payBill.setPayResultCode(TransReturnCode.ERROR.getCode());
            payBill.setPayResultNote(TransReturnCode.ERROR.getMessage());
            payBillService.updateFail(payBill);
            notify(payBill);
            return getResult(payBill);
        }
        try {
            PayBill payBillDb = channelTransFactory.getChannelTrans(payBill).channelTrans(payBill);
            notify(payBillDb);
            return getResult(payBillDb);
        } catch (BizException t) {
            Response response = Response.buildResult(t);
            if (PayTypeEnum.MICROPAY.code.equals(payBill.getPayType())) {
                response.setState(TransStateEnum.OVERTIME.code);
            }
            return response;
        } catch (Throwable t) {
            log.error("处理异常", t);
            Response response = Response.buildResult(TransReturnCode.ERROR);
            if (PayTypeEnum.MICROPAY.code.equals(payBill.getPayType())) {
                response.setState(TransStateEnum.OVERTIME.code);
            }
            return response;
        }
    }

    public void notify(PayBill payBill) {
        try {
            if (payBill == null) {
                log.debug("通知信息为空");
                return;
            }
            if (!TradeContextHolder.getTradeContext().isNotifyFlag()) {
                log.debug("不需要通知");
                return;
            }

            if (payBill.getState() != TransStateEnum.SUCCESS.code
                    && payBill.getState() == TransStateEnum.FAIL.code) {
                log.debug("非最终状态无需通知:{}状态:{}", payBill.getTransId(), payBill.getState());
                return;
            }
            //最终成功失败才发通知
            TransNotify transNotify = new TransNotify();
            transNotify.setTransId(payBill.getTransId());
            transNotify.setTransType(payBill.getTransType());
            transNotify.setMerchantNo(payBill.getMerchantNo());
            transNotify.setOrderDate(payBill.getOrderDate());
            transNotify.setOrderId(payBill.getOrderId());
            transNotify.setOrderAmount(payBill.getAmount());
            transNotify.setTransId(payBill.getTransId());
            transNotify.setConsumerFee(payBill.getConsumerFee());
            transNotify.setMerchantFee(payBill.getMerchantFee());
            transNotify.setState(payBill.getState());
            transNotify.setNotifyUrl(payBill.getNotifyUrl());
            transNotify.setResultCode(payBill.getPayResultCode());
            transNotify.setResultNote(payBill.getPayResultNote());
            mqOutput.tradeNotify().send(MessageBuilder.withPayload(transNotify).build());
            //是否要更新db
            if (NotifyStateEnum.NOTIFY_WAIT.code != payBill.getNotifyState()) {
                payBillService.updateNotifyState(payBill.getTransId(), NotifyStateEnum.NOTIFY_WAIT.code);
            }
        } catch (Throwable t) {
            //notify不能抛出异常影响结果
            log.error("通知处理异常", t);
        }
    }

    public Response<TransResponse> getResult(PayBill payBill) {
        if (payBill == null) {
            return Response.buildResult(TransReturnCode.DATA_NOT_EXISTS).setState(TransStateEnum.QUERY_FAIL.code);
        }
        //默认为已受理
        int state = TransStateEnum.ACCEPT.code;
        //最终状态
        if (payBill != null) {
            //如果产生了支付明细则以支付明细状态为准
            if (payBill.getState() == TransStateEnum.SUCCESS.code
                    || payBill.getState() == TransStateEnum.FAIL.code
                    || payBill.getState() == TransStateEnum.CONFIRM.code) {
                state = payBill.getState();
            }
        } else {
            if (payBill.getState() == TransStateEnum.SUCCESS.code
                    || payBill.getState() == TransStateEnum.SUCCESS_PROCESS.code
                    || payBill.getState() == TransStateEnum.FAIL.code
                    || payBill.getState() == TransStateEnum.FAIL_PROCESS.code
                    || payBill.getState() == TransStateEnum.FAIL_CLOSE.code
                    || payBill.getState() == TransStateEnum.CONFIRM.code
            ) {
                state = payBill.getState();
            } else if (payBill.getState() == TransStateEnum.WAIT.code) {
                //收银台订单可以继续支付,判断订单是否超时关闭
                if (payBill.getTimeExpire() != null && DateUtil.compare(new Date(), payBill.getTimeExpire()) > 0) {
                    payBillService.updateFailClose(payBill);
                }
                state = payBill.getState();
            }
        }
        //订单信息
        TransResponse transResponse = new TransResponse();
        transResponse.setTransId(payBill.getTransId());
        transResponse.setMerchantNo(payBill.getMerchantNo());
        transResponse.setPrepayId(payBill.getChannelPrepayId());
        transResponse.setCodeUrl(payBill.getChannelCodeUrl());
        transResponse.setWebUrl(payBill.getChannelWebUrl());
        Response response = Response.buildResult(payBill.getPayResultCode(), payBill.getPayResultNote());
        return response.setState(state).setData(transResponse);
    }
}
