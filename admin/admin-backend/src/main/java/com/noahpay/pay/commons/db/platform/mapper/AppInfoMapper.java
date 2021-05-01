package com.noahpay.pay.commons.db.platform.mapper;

import com.noahpay.pay.commons.db.platform.model.AppInfo;
import com.kalvan.db.mybatis.IBaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * app信息Mapper
 *
 * @author kalvan.tools:kalvan
 */
@Repository
public interface AppInfoMapper extends IBaseMapper<AppInfo> {
    List<AppInfo> getAllApp();
}
