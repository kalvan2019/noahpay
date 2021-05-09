package com.noahpay.pay.trade.service;

import com.kalvan.client.constant.CommonStateEnum;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Response;
import com.kalvan.client.model.ReturnCode;
import com.kalvan.web.util.DateUtil;
import com.noahpay.pay.channel.bean.res.ChannelTransResponse;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.trade.mapper.AsyncEventHandleMapper;
import com.noahpay.pay.commons.db.trade.mapper.PayBillMapper;
import com.noahpay.pay.commons.db.trade.model.AsyncEventHandle;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.trade.constant.*;
import com.noahpay.pay.trade.context.TradeContextHolder;
import com.noahpay.pay.trade.process.statistics.PayBillStatistics;
import com.noahpay.pay.trade.util.TransIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 快捷支付Service实现类
 *
 * @author chenliang
 */
@Slf4j
@Service
public class PayBillService {
    @Resource
    PayBillMapper payBillMapper;
    @Resource
    AsyncEventHandleMapper asyncEventHandleMapper;
    @Resource
    PayBillStatistics payBillStatistics;

    public PayBill queryByTransId(String transId) {
        if (StringUtils.isBlank(transId)) {
            throw new BizException(TransReturnCode.PARAM_NOT_NULL.formatMessage("transId"));
        }
        PayBill bill = payBillMapper.selectByUk(transId);
        if (bill == null) {
            throw new BizException(TransReturnCode.DATA_NOT_EXISTS.formatMessage(transId));
        }
        return bill;
    }

    public PayBill queryByOrderId(Long merchantNo, Integer orderDate, String orderId) {
        PayBill bill = payBillMapper.queryByOrderId(merchantNo, orderDate, orderId);
        if (bill == null) {
            throw new BizException(TransReturnCode.DATA_NOT_EXISTS);
        }
        return bill;
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public void save(PayBill payBill) {
        log.info("订单信息保存");
        payBillMapper.insertSelective(payBill);
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int updateRoute(PayBill bill) {
        log.info("路由信息更新");
        Integer oldState = bill.getState();
        bill.setState(TransStateEnum.ROUTE.code);
        return payBillMapper.updateRoute(bill, oldState);
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int updateFee(PayBill bill) {
        log.info("计费信息更新");
        bill.setState(TransStateEnum.FEE.code);
        return payBillMapper.updateFee(bill, TransStateEnum.ROUTE.code);
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int updateSendChannel(PayBill bill) {
        log.info("发往通道信息更新");
        bill.setState(TransStateEnum.SEND.code);
        return payBillMapper.updateSendChannel(bill, TransStateEnum.FEE.code);
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int updateSendChannelConfirm(PayBill bill) {
        log.info("发往通道信息更新");
        bill.setState(TransStateEnum.SEND.code);
        return payBillMapper.updateSendChannel(bill, TransStateEnum.CONFIRM.code);
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public void updateFail(PayBill bill) {
        log.info("状态更新为失败");
        //支付明細状态更新
        bill.setState(TransStateEnum.FAIL.code);
        bill.setPayResultNote(StringUtils.left(bill.getPayResultNote(), 100));
        payBillMapper.updateFail(bill);
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public void updateFailClose(PayBill bill) {
        log.info("状态更新为失败");
        bill.setState(TransStateEnum.FAIL_CLOSE.code);
        ReturnCode returnCode = TransReturnCode.FAIL.formatMessage("超时关闭");
        bill.setPayResultCode(returnCode.getCode());
        bill.setPayResultNote(returnCode.getMessage());
        payBillMapper.updateFail(bill);
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public void updateNotifyState(String transId, Integer notifyState) {
        log.info("交易通知状态更新");
        PayBill bill = new PayBill();
        bill.setTransId(transId);
        bill.setNotifyState(notifyState);
        payBillMapper.updateByUkSelective(bill);
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public PayBill updateChannelResponse(PayBill payBill, Response<ChannelTransResponse> response) {
        log.info("通道返回结果处理");
        if (response == null) {
            return payBill;
        }
        PayBill payBillDb;
        try {
            if (payBill == null) {
                //mysql原因不能直接去锁可能会导致其它地方用transId去锁时有死锁,先查出来再去锁
                payBill = payBillMapper.queryBillByChannelSendSn(
                        response.getData().getChannelNo(),
                        response.getData().getChannelSendSn(),
                        TransIdUtils.getOrderDate(response.getData().getChannelSendSn()));
            }
            payBillDb = payBillMapper.selectByUkLock(payBill);
        } catch (Throwable t) {
            throw new BizException(TransReturnCode.DATA_QUERY_ERROR, t);
        }
        if (payBillDb == null) {
            throw new BizException(TransReturnCode.DATA_QUERY_ERROR);
        }
        if (payBillDb.getState() == TransStateEnum.SUCCESS.code ||
                payBillDb.getState() == TransStateEnum.FAIL.code) {
            //已经是最终状态则直接跳过
            TradeContextHolder.getTradeContext().setNotifyFlag(false);
            return payBillDb;
        }
        //获取返回参数如果存在则不要覆盖
        ChannelTransResponse channelTransResponse = response.getData();
        if (channelTransResponse != null) {
            if (channelTransResponse.getChannelRecvAmount() != null &&
                    !channelTransResponse.getChannelRecvAmount().equals(payBillDb.getChannelAmount())) {
                throw new BizException(TransReturnCode.CODE_2705);
            }
            if (StringUtils.isBlank(payBillDb.getChannelRecvSn()) && StringUtils.isNotBlank(channelTransResponse.getChannelRecvSn())) {
                payBillDb.setChannelRecvSn(channelTransResponse.getChannelRecvSn());
            }
            if (payBillDb.getChannelAccountDate() == 0 && channelTransResponse.getChannelRecvAccountDate() != null) {
                payBillDb.setChannelAccountDate(channelTransResponse.getChannelRecvAccountDate());
            }
            if (StringUtils.isNotBlank(channelTransResponse.getChannelRecvExt()) && !"{}".equals(channelTransResponse.getChannelRecvExt())) {
                payBillDb.setChannelRecvExt(channelTransResponse.getChannelRecvExt());
            }
            if (StringUtils.isBlank(payBillDb.getChannelPrepayId()) && StringUtils.isNotBlank(channelTransResponse.getChannelPrepayId())) {
                payBillDb.setChannelPrepayId(channelTransResponse.getChannelPrepayId());
            }
            if (StringUtils.isBlank(payBillDb.getChannelWebUrl()) && StringUtils.isNotBlank(channelTransResponse.getChannelWebUrl())) {
                payBillDb.setChannelWebUrl(channelTransResponse.getChannelWebUrl());
            }
            if (StringUtils.isBlank(payBillDb.getChannelCodeUrl()) && StringUtils.isNotBlank(channelTransResponse.getChannelCodeUrl())) {
                payBillDb.setChannelCodeUrl(channelTransResponse.getChannelCodeUrl());
            }
        }
        //第一次返回码记录
        if (StringUtils.isBlank(payBillDb.getChannelResultCode()) && StringUtils.isNotBlank(response.getCode())) {
            payBillDb.setChannelResultCode(StringUtils.left(response.getCode(), 20));
        }
        if (StringUtils.isBlank(payBillDb.getChannelResultNote()) && StringUtils.isNotBlank(response.getMessage())) {
            payBillDb.setChannelResultNote(StringUtils.left(response.getMessage(), 100));
        }
        payBillDb.setChannelRecvTime(DateUtil.date());
        if (CommonStateEnum.OVERTIME.code == response.getState()) {
            payBillDb.setState(TransStateEnum.OVERTIME.code);
            payBillDb.setPayResultCode(TransReturnCode.OVERTIME.getCode());
            payBillDb.setPayResultNote(TransReturnCode.OVERTIME.getMessage());
        } else if (CommonStateEnum.ACCEPT.code == response.getState()) {
            payBillDb.setState(TransStateEnum.ACCEPT.code);
            payBillDb.setPayResultCode(TransReturnCode.PROCESS.getCode());
            payBillDb.setPayResultNote(TransReturnCode.PROCESS.getMessage());
        } else if (CommonStateEnum.CONFIRM.code == response.getState()) {
            payBillDb.setState(TransStateEnum.CONFIRM.code);
            payBillDb.setPayResultCode(TransReturnCode.CONFIRM.getCode());
            payBillDb.setPayResultNote(TransReturnCode.CONFIRM.getMessage());
        } else if (CommonStateEnum.FAIL.code == response.getState()) {
            //最终返回码记录
            if (StringUtils.isNotBlank(response.getCode())) {
                payBillDb.setChannelResultCode(StringUtils.left(response.getCode(), 20));
            }
            if (StringUtils.isNotBlank(response.getMessage())) {
                payBillDb.setChannelResultNote(StringUtils.left(response.getMessage(), 100));
            }
            payBillDb.setState(TransStateEnum.FAIL.code);
            payBillDb.setPayResultCode(TransReturnCode.FAIL.getCode());
            payBillDb.setPayResultNote(payBillDb.getChannelResultNote());
        } else if (CommonStateEnum.SUCCESS.code == response.getState()) {
            //最终返回码记录
            if (StringUtils.isNotBlank(response.getCode())) {
                payBillDb.setChannelResultCode(StringUtils.left(response.getCode(), 20));
            }
            if (StringUtils.isNotBlank(response.getMessage())) {
                payBillDb.setChannelResultNote(StringUtils.left(response.getMessage(), 100));
            }
            payBillDb.setState(TransStateEnum.SUCCESS.code);
            payBillDb.setPayResultCode(TransReturnCode.SUCCESS.getCode());
            payBillDb.setPayResultNote(TransReturnCode.SUCCESS.getMessage());
        } else {
            //未知状态
            throw new BizException(TransReturnCode.CODE_2701);
        }
        payBillMapper.updateChannelResponse(payBillDb);
        // 异步处理事件
        syncUpdateTransBill(payBillDb);
        return payBillDb;
    }

    /**
     * TODO 如果收银台模式可以复用
     * 根据支付明细结果同步交易流水状态
     */
    private void syncUpdateTransBill(PayBill payBill) {
        AsyncEventHandle asyncEventHandle = new AsyncEventHandle();
        asyncEventHandle.setTransId(payBill.getTransId());
        asyncEventHandle.setPayId(payBill.getId());
        asyncEventHandle.setTransDate(payBill.getOrderDate());
        asyncEventHandle.setTransType(payBill.getTransType());
        asyncEventHandle.setDealTimes(0);
        asyncEventHandle.setState(EventStateEnum.WAIT.code);
        if (payBill.getState() == TransStateEnum.SUCCESS.code) {
            payBillStatistics.successCount(payBill);
        } else if (payBill.getState() == TransStateEnum.ACCEPT.code) {
            //已受理异步查询
            asyncEventHandle.setEventType(EventTypeEnum.ACCEPT.code);
            asyncEventHandle.setStrategyType(StrategyTypeEnum.SUCCESS.code);
        } else if (payBill.getState() == TransStateEnum.OVERTIME.code) {
            //超时处理异步查询
            asyncEventHandle.setEventType(EventTypeEnum.OVERTIME.code);
            asyncEventHandle.setStrategyType(StrategyTypeEnum.REPEAT.code);
        }
        if (asyncEventHandle.getEventType() != null) {
            asyncEventHandle.setResultCode(payBill.getPayResultCode());
            asyncEventHandle.setResultNote(payBill.getPayResultNote());
            AsyncEventHandle temp;
            try {
                temp = asyncEventHandleMapper.selectByUkLock(payBill.getTransId());
            } catch (Throwable t) {
                throw new BizException(TransReturnCode.DATA_QUERY_ERROR, t);
            }
            if (temp == null) {
                try {
                    asyncEventHandleMapper.insertSelective(asyncEventHandle);
                } catch (Throwable t) {
                    //并发时处理，不抛出异常
                }
            } else {
                if (!temp.getEventType().equals(asyncEventHandle.getEventType())) {
                    asyncEventHandleMapper.updateAsyncEventHandle(asyncEventHandle);
                    log.info("更新交易事件成功：{}", temp.getTransId());
                }
            }
        }
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public void updateRefundIng(String transId) {
        int updateRow = payBillMapper.updateRefundIng(transId, TransStateEnum.REFUND_ING.code, TransStateEnum.SUCCESS.code);
        if (updateRow != 1) {
            log.error("更新为退款处理中状态异常");
            throw new BizException(TransReturnCode.CODE_2416);
        }
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public void updateRefundSuccess(String transId) {
        int updateRow = payBillMapper.updateRefundSuccess(transId, TransStateEnum.REFUND_SUCCESS.code, TransStateEnum.REFUND_ING.code);
        if (updateRow != 1) {
            log.error("更新为退款成功状态异常");
            throw new BizException(TransReturnCode.DATA_SAVE_ERROR);
        }
    }

    @Transactional(value = DataSourceConstants.TRADE_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public void updateRefundReturn(String transId) {
        int updateRow = payBillMapper.updateRefundReturn(transId, TransStateEnum.SUCCESS.code, TransStateEnum.REFUND_ING.code);
        if (updateRow != 1) {
            log.error("撤销退款异常");
            throw new BizException(TransReturnCode.DATA_SAVE_ERROR);
        }
    }
}
