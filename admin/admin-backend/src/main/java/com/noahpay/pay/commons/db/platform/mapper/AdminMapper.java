package com.noahpay.pay.commons.db.platform.mapper;

import com.noahpay.pay.commons.db.platform.model.Admin;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 管理员Mapper
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Repository
public interface AdminMapper extends IBaseMapper<Admin> {

    @Override
    List<Admin> queryPage(@Param("params") Map<String, Object> params);

    /**
     * 查找用户
     *
     * @param userCode 用户号
     * @return
     */
    Admin selectByUserCode(String userCode);

    /**
     * 删除非系统管理员的普通管理员
     *
     * @param id
     * @return
     */
    int deleteNotSystemAdminById(Long id);
}
