package com.noahpay.pay.platform.system.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.ParamsDecrypted;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.excel.ExcelUtil;
import com.noahpay.pay.platform.system.service.AdminService;
import com.kalvan.admin.valid.Add;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.mapper.RoleMapper;
import com.noahpay.pay.commons.db.platform.model.Admin;
import com.noahpay.pay.commons.db.platform.model.Role;
import com.noahpay.pay.commons.dto.platform.AdminImport;
import com.kalvan.admin.log.LogType;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 管理员Controller
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@SysLog(group = LogGroup.ADMIN)
@RestController
@RequestMapping("platform/system/admin")
@Slf4j
public class AdminController extends BaseController {
    private static final int DOWNLOAD_MAX_COUNT = 20000;
    @Resource
    private AdminService adminService;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:system:admin:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<Admin> data = adminService.queryPage(pageInfo, params);
        for (Admin admin : data) {
            List<Role> roles = roleMapper.selectRoleListByAdminId(admin.getId());
            admin.setRoles(roles);
        }
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:system:admin:info")
    public WebResponse info(@PathVariable("id") Long id) {
        Admin admin = adminService.findById(id);
        if (admin == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        List<Role> roles = roleMapper.selectRoleListByAdminId(admin.getId());
        admin.setRoles(roles);
        admin.setRoleId(roles.stream().map(Role::getId).toArray(Long[]::new));
        return WebResponse.buildSuccess().putData(admin);
    }

    /**
     * 下载
     *
     * @param params 查询参数
     */
    @SysLog(remark = "导出管理员", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("platform:system:admin:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page data = adminService.queryPage(pageInfo, params);
        String filename = "管理员";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", "20000"));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        ExcelUtil.download(filename, response, Admin.class, data);
    }

    /**
     * 新增管理员
     *
     * @param adminImport adminImport
     * @return WebResponse
     */
    @SysLog(remark = "新增管理员", type = LogType.ADD)
    @PostMapping("add")
    @Permission("platform:system:admin:add")
    public WebResponse add(@Validated({Add.class}) AdminImport adminImport) {
        adminService.add(BeanUtil.toBean(adminImport, Admin.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 管理员导入模板下载
     *
     * @param response 流
     */
    @GetMapping("downloadTemplate")
    @Permission("platform:system:admin:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/platform/管理员-模板.xls", response);
    }

    /**
     * 导入管理员
     *
     * @param file 上传文件
     * @return WebResponse
     */
    @SysLog(remark = "导入管理员", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("platform:system:admin:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<AdminImport> importList = ExcelUtil.importExcel(file, 1, 1, AdminImport.class);
        for (AdminImport adminImport : importList) {
            adminService.add(BeanUtil.toBean(adminImport, Admin.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改管理员
     *
     * @param adminImport adminImport
     * @return WebResponse
     */
    @SysLog(remark = "修改管理员", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("platform:system:admin:edit")
    public WebResponse edit(@Validated({Edit.class}) AdminImport adminImport) {
        if (adminImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = adminService.edit(BeanUtil.toBean(adminImport, Admin.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除管理员
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除管理员", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:system:admin:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = adminService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }
}
