package com.noahpay.pay.platform.gateway.controller;


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
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.model.GatewayRoute;
import com.noahpay.pay.commons.dto.platform.GatewayRouteImport;
import com.kalvan.db.mybatis.PageInfo;
import com.noahpay.pay.platform.gateway.service.GatewayRouteService;
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
 * 网关路由表Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.GATEWAY_ROUTE)
@RestController
@RequestMapping("platform/gateway/route")
@Slf4j
public class GatewayRouteController extends BaseController {
    @Resource
    GatewayRouteService gatewayRouteService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:gateway:route:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<GatewayRoute> data = gatewayRouteService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:gateway:route:info")
    public WebResponse info(@PathVariable("id") Long id) {
        GatewayRoute gatewayRoute = gatewayRouteService.findById(id);
        if (gatewayRoute == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(gatewayRoute);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出路由信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("platform:gateway:route:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<GatewayRoute> data = gatewayRouteService.queryPage(pageInfo, params);
        String filename = "网关路由表";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        ExcelUtil.download(filename, response, GatewayRoute.class, data);
    }

    /**
     * 新增网关路由表
     *
     * @param gatewayRouteImport gatewayRouteImport
     * @return WebResponse
     */
    @SysLog(remark = "新增网关路由表", type = LogType.ADD)
    @PostMapping("add")
    @Permission("platform:gateway:route:add")
    public WebResponse add(@Validated({Add.class}) GatewayRouteImport gatewayRouteImport) {
        gatewayRouteService.add(BeanUtil.toBean(gatewayRouteImport, GatewayRoute.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 网关路由表导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("platform:gateway:route:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/platform/网关路由表-模板.xls", response);
    }

    /**
     * 导入网关路由表
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入网关路由表", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("platform:gateway:route:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<GatewayRouteImport> importList = ExcelUtil.importExcel(file, 1, 1, GatewayRouteImport.class);
        for (GatewayRouteImport gatewayRouteImport : importList) {
            gatewayRouteService.add(BeanUtil.toBean(gatewayRouteImport, GatewayRoute.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改网关路由表
     *
     * @param gatewayRouteImport gatewayRouteImport
     * @return WebResponse
     */
    @SysLog(remark = "修改网关路由表", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("platform:gateway:route:edit")
    public WebResponse edit(@Validated({Edit.class}) GatewayRouteImport gatewayRouteImport) {
        if (gatewayRouteImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = gatewayRouteService.edit(BeanUtil.toBean(gatewayRouteImport, GatewayRoute.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除网关路由表
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除网关路由表", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:gateway:route:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = gatewayRouteService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

    @Permission("platform:gateway:route:updateCache")
    @PostMapping("updateCache")
    public WebResponse updateCache(String routeId) {
        gatewayRouteService.updateCache(routeId);
        return WebResponse.buildSuccess();
    }

}
