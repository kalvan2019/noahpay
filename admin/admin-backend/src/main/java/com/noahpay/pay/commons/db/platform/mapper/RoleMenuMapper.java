package com.noahpay.pay.commons.db.platform.mapper;

import com.noahpay.pay.commons.db.platform.model.RoleMenu;
import com.kalvan.db.mybatis.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author chenliang
 */
@Service
public interface RoleMenuMapper extends IBaseMapper<RoleMenu> {
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
