package com.noahpay.pay.commons.db.platform.mapper;

import com.noahpay.pay.commons.db.platform.model.GatewayService;
import com.kalvan.db.mybatis.IBaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 服务发布Mapper
 *
 * @author kalvan.tools:kalvan
 */
@Repository
public interface GatewayServiceMapper extends IBaseMapper<GatewayService> {
    List<GatewayService> getAllService();
}
