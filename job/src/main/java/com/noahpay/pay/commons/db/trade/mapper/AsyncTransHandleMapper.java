package com.noahpay.pay.commons.db.trade.mapper;

import com.noahpay.pay.commons.db.trade.model.AsyncTransHandle;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 交易异步处理任务表Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface AsyncTransHandleMapper extends IBaseMapper<AsyncTransHandle> {
    /**
     * 查询需要处理的交易
     */
    List<AsyncTransHandle> queryAsyncHandle(@Param("queryRowNum") int queryRowNum);

    /**
     * 状态由待处理到处理中
     */
    int updateStateProcess(@Param("id") Long id);

    /**
     * 更新终态
     */
    int updateStateEnd(AsyncTransHandle asyncTransHandle);

    /**
     * 移除两天前已完结的数据
     */
    int cleanHistory();
}
