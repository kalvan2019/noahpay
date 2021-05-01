package com.noahpay.pay.commons.db.trade.mapper;

import com.noahpay.pay.commons.db.trade.model.AsyncEventHandle;
import com.kalvan.db.mybatis.IBaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 异步事务控制Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface AsyncEventHandleMapper extends IBaseMapper<AsyncEventHandle> {
    int updateAsyncEventHandle(AsyncEventHandle asyncEventHandle);
}
