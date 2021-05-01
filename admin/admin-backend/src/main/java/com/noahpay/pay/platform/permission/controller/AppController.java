package com.noahpay.pay.platform.permission.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.ParamsDecrypted;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.admin.excel.ExcelUtil;
import com.kalvan.admin.log.LogType;
import com.kalvan.admin.valid.Add;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import com.noahpay.pay.platform.permission.service.AppService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.model.Dict;
import com.noahpay.pay.commons.dto.platform.AppImport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 应用系统Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.DICT)
@RestController
@RequestMapping("platform/permission/app")
@Slf4j
public class AppController extends BaseController {
    @Resource
    AppService appService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:permission:app:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<Dict> data = appService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:permission:app:info")
    public WebResponse info(@PathVariable("id") Integer id) {
        Dict dict = appService.findById(id);
        if (dict == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(dict);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出应用系统", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("platform:permission:app:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<Dict> data = appService.queryPage(pageInfo, params);
        String filename = "应用系统";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        excludeColumnFiledNames.add("systemCode");
        excludeColumnFiledNames.add("dictType");
        excludeColumnFiledNames.add("dictName");
        excludeColumnFiledNames.add("dictIcon");
        excludeColumnFiledNames.add("selectEnable");
        ExcelUtil.download(filename, response, Dict.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增应用系统
     *
     * @param appImport appImport
     * @return WebResponse
     */
    @SysLog(remark = "新增应用系统", type = LogType.ADD)
    @PostMapping("add")
    @Permission("platform:permission:app:add")
    public WebResponse add(@Validated({Add.class}) AppImport appImport) {
        appService.add(BeanUtil.toBean(appImport, Dict.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 应用系统导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("platform:permission:app:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/platform/应用系统-模板.xls", response);
    }

    /**
     * 导入应用系统
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入应用系统", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("platform:permission:app:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<AppImport> importList = ExcelUtil.importExcel(file, 1, 1, AppImport.class);
        for (AppImport appImport : importList) {
            appService.add(BeanUtil.toBean(appImport, Dict.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改应用系统
     *
     * @param appImport appImport
     * @return WebResponse
     */
    @SysLog(remark = "修改应用系统", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("platform:permission:app:edit")
    public WebResponse edit(@Validated({Edit.class}) AppImport appImport) {
        if (appImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = appService.edit(BeanUtil.toBean(appImport, Dict.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除应用系统
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除应用系统", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:permission:app:delete")
    public WebResponse delete(Integer[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = appService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
