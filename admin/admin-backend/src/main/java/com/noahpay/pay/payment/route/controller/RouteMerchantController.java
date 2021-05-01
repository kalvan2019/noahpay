package com.noahpay.pay.payment.route.controller;


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
import com.noahpay.pay.payment.route.service.RouteMerchantService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.channel.model.RouteMerchant;
import com.noahpay.pay.commons.dto.channel.RouteMerchantImport;
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
 * 商户路由配置Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.ROUTE_MERCHANT)
@RestController
@RequestMapping("payment/route/routemerchant")
@Slf4j
public class RouteMerchantController extends BaseController {
    @Resource
    RouteMerchantService routeMerchantService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:route:routemerchant:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<RouteMerchant> data = routeMerchantService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:route:routemerchant:info")
    public WebResponse info(@PathVariable("id") Long id) {
        RouteMerchant routeMerchant = routeMerchantService.findById(id);
        if (routeMerchant == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(routeMerchant);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出商户路由配置", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:route:routemerchant:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<RouteMerchant> data = routeMerchantService.queryPage(pageInfo, params);
        String filename = "商户路由配置";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, RouteMerchant.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增商户路由配置
     *
     * @param routeMerchantImport routeMerchantImport
     * @return WebResponse
     */
    @SysLog(remark = "新增商户路由配置", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:route:routemerchant:add")
    public WebResponse add(@Validated({Add.class}) RouteMerchantImport routeMerchantImport) {
        routeMerchantService.add(BeanUtil.toBean(routeMerchantImport, RouteMerchant.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 商户路由配置导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:route:routemerchant:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/商户路由配置-模板.xls", response);
    }

    /**
     * 导入商户路由配置
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入商户路由配置", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:route:routemerchant:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<RouteMerchantImport> importList = ExcelUtil.importExcel(file, 1, 1, RouteMerchantImport.class);
        for (RouteMerchantImport routeMerchantImport : importList) {
            routeMerchantService.add(BeanUtil.toBean(routeMerchantImport, RouteMerchant.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改商户路由配置
     *
     * @param routeMerchantImport routeMerchantImport
     * @return WebResponse
     */
    @SysLog(remark = "修改商户路由配置", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:route:routemerchant:edit")
    public WebResponse edit(@Validated({Edit.class}) RouteMerchantImport routeMerchantImport) {
        if (routeMerchantImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = routeMerchantService.edit(BeanUtil.toBean(routeMerchantImport, RouteMerchant.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除商户路由配置
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除商户路由配置", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("payment:route:routemerchant:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = routeMerchantService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
