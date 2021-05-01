package com.noahpay.pay.commons.db.trade.mapper;

import com.kalvan.db.mybatis.IBaseMapper;
import com.noahpay.pay.commons.db.trade.model.TransPayBill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 交易订单支付明细Mapper
 *
 * @author kalvan.tools:kalvan
 */
@Repository
public interface TransPayBillMapper extends IBaseMapper<TransPayBill> {

    /**
     * 创建交易表
     *
     * @param date 日期
     * @return 结果
     */
    int createBillByDate(@Param("date") String date);
}
