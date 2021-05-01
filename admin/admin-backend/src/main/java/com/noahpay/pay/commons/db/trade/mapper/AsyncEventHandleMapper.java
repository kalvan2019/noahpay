package com.noahpay.pay.commons.db.trade.mapper;

import com.kalvan.db.mybatis.IBaseMapper;
import com.noahpay.pay.commons.db.trade.model.AsyncEventHandle;
import org.springframework.stereotype.Repository;

/**
 * 异步事务控制Mapper
 *
 * @author kalvan.tools:kalvan
 */
@Repository
public interface AsyncEventHandleMapper extends IBaseMapper<AsyncEventHandle> {

}
