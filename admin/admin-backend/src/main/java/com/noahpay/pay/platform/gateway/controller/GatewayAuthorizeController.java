package com.noahpay.pay.platform.gateway.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.ParamsDecrypted;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.excel.ExcelUtil;
import com.noahpay.pay.commons.db.platform.model.AppInfo;
import com.noahpay.pay.commons.db.platform.model.GatewayService;
import com.noahpay.pay.platform.gateway.service.AppInfoService;
import com.noahpay.pay.platform.gateway.service.GatewayAuthorizeService;
import com.kalvan.admin.valid.Add;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.model.GatewayAuthorize;
import com.noahpay.pay.commons.dto.platform.GatewayAuthorizeImport;
import com.kalvan.admin.log.LogType;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.db.mybatis.PageInfo;
import com.noahpay.pay.platform.gateway.service.GatewayServiceService;
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
 * 接入方服务授权表Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.GATEWAY_AUTHORIZE)
@RestController
@RequestMapping("platform/gateway/authorize")
@Slf4j
public class GatewayAuthorizeController extends BaseController {
    @Resource
    GatewayAuthorizeService gatewayAuthorizeService;
    @Resource
    GatewayServiceService gatewayServiceService;
    @Resource
    AppInfoService appInfoService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:gateway:authorize:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<GatewayAuthorize> data = gatewayAuthorizeService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 获取所有的服务列表
     *
     * @return WebResponse
     */
    @SysLog(remark = "获取所有的服务列表", type = LogType.ADD)
    @PostMapping("getAllService")
    @Permission("platform:gateway:authorize:list")
    public WebResponse getAllService() {
        List<GatewayService> list = gatewayServiceService.getAllService();
        return WebResponse.buildSuccess().putData(list);
    }
    /**
     * 获取所有的app列表
     *
     * @return WebResponse
     */
    @SysLog(remark = "获取所有的app列表", type = LogType.ADD)
    @PostMapping("getAllApp")
    @Permission("platform:gateway:authorize:list")
    public WebResponse getAllApp() {
        List<AppInfo> list = appInfoService.getAllApp();
        return WebResponse.buildSuccess().putData(list);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:gateway:authorize:info")
    public WebResponse info(@PathVariable("id") Long id) {
        GatewayAuthorize gatewayAuthorize = gatewayAuthorizeService.findById(id);
        if (gatewayAuthorize == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(gatewayAuthorize);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出接口授权信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("platform:gateway:authorize:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<GatewayAuthorize> data = gatewayAuthorizeService.queryPage(pageInfo, params);
        String filename = "接入方服务授权表";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        ExcelUtil.download(filename, response, GatewayAuthorize.class, data);
    }

    /**
     * 新增接入方服务授权表
     *
     * @param gatewayAuthorizeImport gatewayAuthorizeImport
     * @return WebResponse
     */
    @SysLog(remark = "新增接入方服务授权表", type = LogType.ADD)
    @PostMapping("add")
    @Permission("platform:gateway:authorize:add")
    public WebResponse add(@Validated({Add.class}) GatewayAuthorizeImport gatewayAuthorizeImport) {
        gatewayAuthorizeService.add(BeanUtil.toBean(gatewayAuthorizeImport, GatewayAuthorize.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 接入方服务授权表导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("platform:gateway:authorize:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/platform/接入方服务授权表-模板.xls", response);
    }

    /**
     * 导入接入方服务授权表
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入接入方服务授权表", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("platform:gateway:authorize:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<GatewayAuthorizeImport> importList = ExcelUtil.importExcel(file, 1, 1, GatewayAuthorizeImport.class);
        for (GatewayAuthorizeImport gatewayAuthorizeImport : importList) {
            gatewayAuthorizeService.add(BeanUtil.toBean(gatewayAuthorizeImport, GatewayAuthorize.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改接入方服务授权表
     *
     * @param gatewayAuthorizeImport gatewayAuthorizeImport
     * @return WebResponse
     */
    @SysLog(remark = "修改接入方服务授权表", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("platform:gateway:authorize:edit")
    public WebResponse edit(@Validated({Edit.class}) GatewayAuthorizeImport gatewayAuthorizeImport) {
        if (gatewayAuthorizeImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = gatewayAuthorizeService.edit(BeanUtil.toBean(gatewayAuthorizeImport, GatewayAuthorize.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除接入方服务授权表
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除接入方服务授权表", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:gateway:authorize:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = gatewayAuthorizeService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

    @Permission("platform:gateway:authorize:updateCache")
    @PostMapping("updateCache")
    public WebResponse updateCache(String appId, String service) {
        gatewayAuthorizeService.updateCache(appId, service);
        return WebResponse.buildSuccess();
    }

}
