package com.noahpay.pay.commons.db.platform.mapper;

import com.noahpay.pay.commons.db.platform.model.GrayRule;
import com.kalvan.db.mybatis.IBaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 灰度规则配置Mapper
 *
 * @author kalvan.tools:kalvan
 */
@Repository
public interface GrayRuleMapper extends IBaseMapper<GrayRule> {

}
