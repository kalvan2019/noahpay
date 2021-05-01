package com.noahpay.pay.commons.db.trade.mapper;

import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 交易流水Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface PayBillMapper extends IBaseMapper<PayBill> {
    /**
     * 根据订单信息获取流水
     */
    PayBill queryByOrderId(@Param("merchantNo") Long merchantNo, @Param("orderDate") Integer orderDate, @Param("orderId") String orderId);

    /**
     * 根据订单信息获取流水记录
     */
    int queryCountByOrderId(@Param("merchantNo") Long merchantNo, @Param("orderDate") Integer orderDate, @Param("orderId") String orderId);

    /**
     * 记账后更改状态
     */
    int updateAccountSuccess(@Param("bill") PayBill bill, @Param("oldState") Integer oldState);

    /**
     * 更新计费信息
     */
    int updateFee(@Param("bill") PayBill bill, @Param("oldState") Integer oldState);

    /**
     * 更新路由信息
     */
    int updateRoute(@Param("bill") PayBill bill, @Param("oldState") Integer oldState);

    /**
     * 更新发往通道信息
     */
    int updateSendChannel(@Param("bill") PayBill bill, @Param("oldState") Integer oldState);

    /**
     * 更新为超时
     */
    int updateSendChannelOvertime(@Param("bill") PayBill bill);
    /**
     * 更新通道返回结果
     */
    int updateChannelResponse(@Param("bill") PayBill bill);
    /**
     * 更新为失败
     */
    int updateFail(@Param("bill") PayBill bill);
    /**
     * 更新支付明细id
     */
    int updatePayId(@Param("bill") PayBill bill);

    /**
     * 更新为等待异常处理
     */
    int updateWaitAsync(@Param("transId") String transId, @Param("state") int state, @Param("oldState") int oldState);

    /**
     * 更新为异常处理中
     */
    int updateInAsync(@Param("transId") String transId, int state, int oldState);

    /**
     * 更新为退款中
     */
    int updateRefundIng(@Param("transId") String transId, int state, int oldState);

    /**
     * 撤销退款
     */
    int updateRefundReturn(@Param("transId") String transId, int state, int oldState);

    /**
     * 更新为退款成功
     */
    int updateRefundSuccess(@Param("transId") String transId, int state, int oldState);

    /**
     * 根据发往通道流水锁住记录查找
     */
    PayBill queryBillByChannelSendSn(@Param("channelNo") Integer channelNo, @Param("channelSendSn") String channelSendSn, @Param("orderDate") Integer orderDate);

}
