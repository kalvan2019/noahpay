package com.noahpay.pay.commons.db.trade.mapper;

import com.kalvan.db.mybatis.IBaseMapper;
import com.noahpay.pay.commons.db.trade.model.AsyncTransHandle;
import org.springframework.stereotype.Repository;

/**
 * 交易异步处理任务表Mapper
 *
 * @author kalvan.tools:kalvan
 */
@Repository
public interface AsyncTransHandleMapper extends IBaseMapper<AsyncTransHandle> {

}
