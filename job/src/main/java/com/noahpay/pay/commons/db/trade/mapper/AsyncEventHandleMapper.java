package com.noahpay.pay.commons.db.trade.mapper;

import com.noahpay.pay.commons.db.trade.model.AsyncEventHandle;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 异步事务控制Mapper
 *
 * @author kalvan.tools:chenliang
 */
@Repository
public interface AsyncEventHandleMapper extends IBaseMapper<AsyncEventHandle> {
    /**
     * 查询入账的交易
     */
    List<AsyncEventHandle> queryAccountIn(
            @Param("eventType") int eventType,
            @Param("queryRowNum") int queryRowNum,
            @Param("groupRow") int groupRow
    );

    /**
     * 查询undo的交易
     */
    List<AsyncEventHandle> queryAccountOutUndo(
            @Param("eventType") int eventType,
            @Param("queryRowNum") int queryRowNum,
            @Param("groupRow") int groupRow);

    /**
     * 查询非结算待处理入账的交易
     */
    List<AsyncEventHandle> queryOvertime(@Param("queryRowNum") int queryRowNum);

    /**
     * 状态由待处理到处理中
     */
    int updateStateProcess(AsyncEventHandle asyncEventHandle);

    /**
     * 状态由处理中到结果
     */
    int updateStateProcessOver(AsyncEventHandle asyncEventHandle);

    /**
     * 移除两天前已完结的数据
     */
    int cleanHistory();
}
