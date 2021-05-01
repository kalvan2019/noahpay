package com.noahpay.pay.commons.db.trade.mapper;

import com.noahpay.pay.commons.db.trade.model.TransBill;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 交易流水Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface TransBillMapper extends IBaseMapper<TransBill> {
    /**
     * 创建交易表
     *
     * @param date 日期
     * @return 结果
     */
    int createBillByDate(@Param("date") String date);
}
