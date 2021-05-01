package com.noahpay.pay.platform.permission.controller;


import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.admin.log.LogType;
import com.kalvan.admin.valid.Add;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.mapper.MenuMapper;
import com.noahpay.pay.commons.db.platform.mapper.RoleMenuMapper;
import com.noahpay.pay.commons.db.platform.model.Menu;
import com.noahpay.pay.commons.db.platform.model.Role;
import com.noahpay.pay.commons.db.platform.model.RoleMenu;
import com.noahpay.pay.commons.dto.platform.RoleImport;
import com.kalvan.db.mybatis.PageInfo;
import com.noahpay.pay.platform.permission.service.RoleService;
import com.noahpay.pay.platform.system.service.AuthService;
import com.kalvan.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色Controller
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@SysLog(group = LogGroup.ROLE)
@RestController
@RequestMapping("platform/permission/role")
@Slf4j
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;
    @Resource
    private AuthService authService;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private MenuMapper menuMapper;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:permission:role:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page data = roleService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:permission:role:info")
    public WebResponse info(@PathVariable("id") Long id) {
        Role role = roleService.findById(id);
        if (role == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(role);
    }

    /**
     * 新增角色
     *
     * @param roleImport roleImport
     * @return WebResponse
     */
    @SysLog(remark = "新增角色", type = LogType.ADD)
    @PostMapping("add")
    @Permission("platform:permission:role:add")
    public WebResponse add(@Validated({Add.class}) RoleImport roleImport) {
        roleService.add(BeanUtil.toBean(roleImport, Role.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 修改角色
     *
     * @param roleImport roleImport
     * @return WebResponse
     */
    @SysLog(remark = "修改角色", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("platform:permission:role:edit")
    public WebResponse edit(@Validated({Edit.class}) RoleImport roleImport) {
        if (roleImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = roleService.edit(BeanUtil.toBean(roleImport, Role.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除角色
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除角色", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:permission:role:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = roleService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

    @PostMapping("menuList")
    @Permission("platform:permission:role:grant")
    public WebResponse menuList(Long id) {
        List<Menu> roleMenu = menuMapper.selectAllMenuByRoleId(id);
        List<Menu> menuList = authService.selectMenuList(null);
        Map<String,Object> data=new HashMap<>(2);
        data.put("roleMenu", roleMenu);
        data.put("menuList", menuList);
        return WebResponse.buildSuccess().putData(data);
    }

    @SysLog(remark = "角色授权", type = LogType.EDIT)
    @PostMapping("grant")
    @Permission("platform:permission:role:grant")
    public WebResponse grant(Long roleId, Long[] menuIds) {
        if (roleId == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = roleService.grant(roleId, menuIds);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    @GetMapping("allRole")
    public WebResponse allRole() {
        return WebResponse.buildSuccess().putData(roleService.findAll());
    }

    @SysLog(remark = "角色复制", type = LogType.EDIT)
    @PostMapping("copy")
    @Permission("platform:permission:role:grant")
    public WebResponse copy(Long roleId, Long copyRoleId) {
        if (roleId == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        RoleMenu query = new RoleMenu();
        query.setRoleId(copyRoleId);
        List<RoleMenu> list = roleMenuMapper.select(query);
        if (list.isEmpty()) {
            throw new BizException(AdminReturnCode.DATA_NOT_EXISTS);
        }
        List<Long> menuIds = list.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        Long[] longs = new Long[menuIds.size()];
        int num = roleService.grant(roleId, menuIds.toArray(longs));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }
}
