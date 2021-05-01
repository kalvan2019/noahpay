package com.noahpay.pay.commons.db.platform.mapper;

import com.noahpay.pay.commons.db.platform.model.Menu;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单资源Mapper
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Repository
public interface MenuMapper extends IBaseMapper<Menu> {

    @Override
    List<Menu> queryPage(@Param("params") Map<String, Object> params);

    /**
     * 所有权限标识
     *
     * @param systemCode
     * @return
     */
    Set<String> selectAllCode(@Param("systemCode") String systemCode);

    /**
     * 查找菜单
     *
     * @param systemCode
     * @return
     */
    List<Menu> selectAllMenu(@Param("systemCode") String systemCode);

    /**
     * 查询管理员的所有权限标识
     *
     * @param systemCode
     * @param id
     * @return
     */
    Set<String> selectAllCodeByAdminId(@Param("systemCode") String systemCode, @Param("id") Long id);

    /**
     * 查找管理用户拥有所有菜单
     *
     * @param systemCode
     * @param id
     * @return
     */
    List<Menu> selectAllMenuByAdminId(@Param("systemCode") String systemCode, @Param("id") Long id);


    /**
     * 查找角色的权限
     *
     * @param id
     * @return
     */
    List<Menu> selectAllMenuByRoleId(@Param("id") Long id);

    /**
     * 更新子菜单的系统编号
     *
     * @param id
     * @return
     */
    int updateSystemCode(@Param("parentId") Long id, @Param("systemCode") String systemCode);

    /**
     * 删除三级菜单
     *
     * @param id
     */
    int deleteChildrenButton(@Param("id") Long id);

    /**
     * 删除二级菜单
     *
     * @param id
     */
    int deleteChildrenMenu(@Param("id") Long id);
}
