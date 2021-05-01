package com.noahpay.pay.commons.db.platform.mapper;

import com.noahpay.pay.commons.db.platform.model.Log;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 操作日志记录Mapper
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Repository
public interface LogMapper extends IBaseMapper<Log> {

    @Override
    List<Log> queryPage(@Param("params") Map<String, Object> params);


}
