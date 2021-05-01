package com.noahpay.pay.platform.permission.controller;


import cn.hutool.core.bean.BeanUtil;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.log.LogType;
import com.noahpay.pay.platform.system.service.AuthService;
import com.noahpay.pay.platform.permission.service.MenuService;
import com.kalvan.admin.valid.Add;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.model.Menu;
import com.noahpay.pay.commons.dto.platform.MenuImport;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单资源Controller
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@SysLog(group = LogGroup.MENU)
@RestController
@RequestMapping("platform/permission/menu")
@Slf4j
public class MenuController extends BaseController {
    @Resource
    private MenuService menuService;
    @Resource
    private AuthService authService;

    /**
     * 查询所有
     *
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:permission:menu:list")
    public WebResponse list(String systemCode) {
        List<Menu> data = authService.selectMenuList(systemCode);
        return WebResponse.buildSuccess().putData(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:permission:menu:info")
    public WebResponse info(@PathVariable("id") Long id) {
        Menu menu = menuService.findById(id);
        if (menu == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(menu);
    }

    /**
     * 新增菜单资源
     *
     * @param menuImport menuImport
     * @return WebResponse
     */
    @SysLog(remark = "新增菜单资源")
    @PostMapping("add")
    @Permission("platform:permission:menu:add")
    public WebResponse add(@Validated({Add.class}) MenuImport menuImport) {
        menuService.add(BeanUtil.toBean(menuImport, Menu.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 修改菜单资源
     *
     * @param menuImport menuImport
     * @return WebResponse
     */
    @SysLog(remark = "修改菜单资源")
    @PostMapping("edit")
    @Permission("platform:permission:menu:edit")
    public WebResponse edit(@Validated({Edit.class}) MenuImport menuImport) {
        if (menuImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num =  menuService.edit(BeanUtil.toBean(menuImport, Menu.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除菜单资源
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除菜单资源" ,type= LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:permission:menu:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = menuService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

    @PostMapping("priority")
    @Permission("platform:permission:menu:edit")
    public WebResponse editOrder(Long id, Integer priority) {
        if (id == null || priority == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        Menu menu = new Menu();
        menu.setPriority(priority);
        menu.setId(id);
        menuService.edit(menu);
        return WebResponse.buildSuccess();
    }

}
