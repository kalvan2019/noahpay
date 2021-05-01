package com.noahpay.pay.platform.gateway.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.ParamsDecrypted;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.excel.ExcelUtil;
import com.noahpay.pay.platform.gateway.service.GatewayServiceService;
import com.kalvan.admin.valid.Add;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.model.GatewayService;
import com.noahpay.pay.commons.dto.platform.GatewayServiceImport;
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
 * 服务发布Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.GATEWAY_SERVICE)
@RestController
@RequestMapping("platform/gateway/service")
@Slf4j
public class GatewayServiceController extends BaseController {
    @Resource
    GatewayServiceService gatewayServiceService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:gateway:service:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<GatewayService> data = gatewayServiceService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:gateway:service:info")
    public WebResponse info(@PathVariable("id") Long id) {
        GatewayService gatewayService = gatewayServiceService.findById(id);
        if (gatewayService == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(gatewayService);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出接口信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("platform:gateway:service:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<GatewayService> data = gatewayServiceService.queryPage(pageInfo, params);
        String filename = "服务发布";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        ExcelUtil.download(filename, response, GatewayService.class, data);
    }

    /**
     * 新增服务发布
     *
     * @param gatewayServiceImport gatewayServiceImport
     * @return WebResponse
     */
    @SysLog(remark = "新增服务发布", type = LogType.ADD)
    @PostMapping("add")
    @Permission("platform:gateway:service:add")
    public WebResponse add(@Validated({Add.class}) GatewayServiceImport gatewayServiceImport) {
        gatewayServiceService.add(BeanUtil.toBean(gatewayServiceImport, GatewayService.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 服务发布导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("platform:gateway:service:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/platform/服务发布-模板.xls", response);
    }

    /**
     * 导入服务发布
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入服务发布", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("platform:gateway:service:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<GatewayServiceImport> importList = ExcelUtil.importExcel(file, 1, 1, GatewayServiceImport.class);
        for (GatewayServiceImport gatewayServiceImport : importList) {
            gatewayServiceService.add(BeanUtil.toBean(gatewayServiceImport, GatewayService.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改服务发布
     *
     * @param gatewayServiceImport gatewayServiceImport
     * @return WebResponse
     */
    @SysLog(remark = "修改服务发布", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("platform:gateway:service:edit")
    public WebResponse edit(@Validated({Edit.class}) GatewayServiceImport gatewayServiceImport) {
        if (gatewayServiceImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = gatewayServiceService.edit(BeanUtil.toBean(gatewayServiceImport, GatewayService.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除服务发布
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除服务发布", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:gateway:service:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = gatewayServiceService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

    @Permission("platform:gateway:service:updateCache")
    @PostMapping("updateCache")
    public WebResponse updateCache(String service) {
        gatewayServiceService.updateCache(service);
        return WebResponse.buildSuccess();
    }

}
