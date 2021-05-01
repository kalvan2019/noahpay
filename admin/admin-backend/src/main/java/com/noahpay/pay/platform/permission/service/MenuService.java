package com.noahpay.pay.platform.permission.service;

import com.kalvan.client.exception.BizException;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.platform.mapper.MenuMapper;
import com.noahpay.pay.commons.db.platform.mapper.RoleMenuMapper;
import com.noahpay.pay.commons.db.platform.model.Menu;
import com.noahpay.pay.commons.db.platform.model.RoleMenu;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 菜单资源Service实现类
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Service
public class MenuService extends BaseAuditService<Menu> {
    @Resource
    private MenuMapper mapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public void processParams(Map<String, Object> params) {
        //  查询和下载的条件检查
    }

    @Override
    public int add(Menu menu) {
        //  填充数据,选择字段进行检查，更新人，更新时间
        Menu menuQuery = new Menu();
        menuQuery.setCode(menu.getCode());
        if (mapper.selectCount(menuQuery) > 0) {
            throw new BizException(AdminReturnCode.DATA_REPEAT.formatMessage(menu.getCode()));
        }
        if (menu.getPriority() == null) {
            menu.setPriority(0);
        }
        // 维护parentIds
        if (menu.getParentId() == null || menu.getParentId() == 0) {
            menu.setParentId(0L);
        } else {
            Menu parentMenu = mapper.selectByUk(menu.getParentId());
            menu.setSystemCode(parentMenu.getSystemCode());
        }
        if (StringUtils.isBlank(menu.getSystemCode())) {
            throw new BizException(AdminReturnCode.PARAM_NOT_NULL.formatMessage("所属应用"));
        }
        return this.addDataAudit(menu);
    }

    @Override
    public int edit(Menu menu) {
        Menu menuDb = mapper.selectByUk(menu.getId());
        if (menuDb == null) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        //  更新指定字段，更新人
        Menu menuNew = new Menu();
        menuNew.setId(menu.getId());
        menuNew.setSystemCode(menu.getSystemCode());
        menuNew.setTitle(menu.getTitle());
        menuNew.setName(menu.getName());
        menuNew.setType(menu.getType());
        menuNew.setHideChildren(menu.getHideChildren());
        menuNew.setIcon(menu.getIcon());
        menuNew.setPath(menu.getPath());
        menuNew.setRedirect(menu.getRedirect());
        menuNew.setCode(menu.getCode());
        menuNew.setPriority(menu.getPriority());
        menuNew.setParentId(menu.getParentId());
        return this.editDataAudit(menuDb, menuNew);
    }

    @Override
    @Transactional(value = DataSourceConstants.PLATFORM_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public int deleteBatch(Object[] ids) {
        int resultNum = 0;
        for (Object id : ids) {
            Menu menuDb = mapper.selectByUk(id);
            if (menuDb == null) {
                throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
            }
            resultNum = resultNum + this.deleteDataAudit(menuDb);
            //审核通过回调删除子菜单
        }
        return resultNum;
    }

    @Override
    public void auditingNotify(Menu oldObject, Menu newObject) {
        if (newObject == null) {
            // TODO 删除子菜单和角色权限配置，需要记录日志
            //删除子按钮那一级(先删除角色菜单配置再删除角色)
            roleMenuMapper.deleteChildrenButton(oldObject.getId());
            mapper.deleteChildrenButton(oldObject.getId());
            //删除菜单那一级
            roleMenuMapper.deleteChildrenMenu(oldObject.getId());
            mapper.deleteChildrenMenu(oldObject.getId());
            //删除自己
            RoleMenu roleMenuDelete = new RoleMenu();
            roleMenuDelete.setMenuId(oldObject.getId());
            roleMenuMapper.delete(roleMenuDelete);
        }
        if (oldObject != null && newObject != null) {
            //修改
            if (oldObject.getSystemCode() != null && newObject.getSystemCode() != null
                    && !oldObject.getSystemCode().equals(newObject.getSystemCode())) {
                //子菜单systemCode更新
                mapper.updateSystemCode(newObject.getId(), newObject.getSystemCode());
            }
        }
    }
}

