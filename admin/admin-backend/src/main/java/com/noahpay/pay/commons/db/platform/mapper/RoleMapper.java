package com.noahpay.pay.commons.db.platform.mapper;

import com.noahpay.pay.commons.db.platform.model.Role;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色Mapper
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Repository
public interface RoleMapper extends IBaseMapper<Role> {

    @Override
    List<Role> queryPage(@Param("params") Map<String, Object> params);

    /**
     * 根据id查找角色
     *
     * @param id
     * @return
     */
    List<Role> selectRoleListByAdminId(Long id);

}
