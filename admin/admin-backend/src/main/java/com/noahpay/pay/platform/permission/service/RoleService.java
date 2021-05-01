package com.noahpay.pay.platform.permission.service;

import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.AdminRoleMapper;
import com.noahpay.pay.commons.db.platform.mapper.RoleMapper;
import com.noahpay.pay.commons.db.platform.mapper.RoleMenuMapper;
import com.noahpay.pay.commons.db.platform.model.AdminRole;
import com.noahpay.pay.commons.db.platform.model.Role;
import com.noahpay.pay.commons.db.platform.model.RoleMenu;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 角色Service实现类
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Service
public class RoleService extends BaseAuditService<Role> {
    @Resource
    protected RoleMapper mapper;
    @Resource
    AdminRoleMapper adminRoleMapper;
    @Resource
    RoleMenuMapper roleMenuMapper;

    @Override
    public void processParams(Map<String, Object> params) {
        //  查询和下载的条件检查
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int add(Role role) {
        //  填充数据,选择字段进行检查，更新人，更新时间
        Role roleQuery = new Role();
        roleQuery.setRoleName(role.getRoleName());
        if (mapper.selectCount(roleQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT.formatMessage(role.getRoleName()));
        }
        return this.addDataAudit(role);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int edit(Role role) {
        Role roleDb = mapper.selectByUk(role.getId());
        if (roleDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        //  更新指定字段，更新人
        Role roleNew = new Role();
        roleNew.setId(role.getId());
        roleNew.setRoleName(role.getRoleName());
        roleNew.setRoleDesc(role.getRoleDesc());
        roleNew.setState(role.getState());
        return this.editDataAudit(roleDb, roleNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            Role roleDb = mapper.selectByUk(id);
            if (roleDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(roleDb);
        }
        return resultNum;
    }

    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int grant(Long roleId, Long[] menuIds) {
        Role roleDb = mapper.selectByUk(roleId);
        if (roleDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        Role roleNew = new Role();
        roleNew.setId(roleId);
        roleNew.setMenuId(menuIds);
        return this.editDataAudit(roleDb, roleNew);
    }

    @Override
    public void auditingNotify(Role oldObject, Role newObject) {
        if (newObject == null) {
            //清理拥有该角色的管理员
            AdminRole adminRoleDelete = new AdminRole();
            adminRoleDelete.setRoleId(oldObject.getId());
            adminRoleMapper.delete(adminRoleDelete);
            //清理角色对应菜单
            RoleMenu roleMenuDelete = new RoleMenu();
            roleMenuDelete.setRoleId(oldObject.getId());
            roleMenuMapper.delete(roleMenuDelete);
        } else if (newObject != null && newObject.getMenuId() != null) {
            int resultNum = 0;
            RoleMenu roleMenuDelete = new RoleMenu();
            roleMenuDelete.setRoleId(newObject.getId());
            roleMenuMapper.delete(roleMenuDelete);
            if (ArrayUtils.isNotEmpty(newObject.getMenuId())) {
                for (Long menuId : newObject.getMenuId()) {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setMenuId(menuId);
                    roleMenu.setRoleId(newObject.getId());
                    try {
                        resultNum = resultNum + roleMenuMapper.insertSelective(roleMenu);
                    } catch (Exception e) {
                        // 重复提交重复跳过
                    }
                }
            }
        }

    }
}
