package com.noahpay.pay.platform.gateway.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.excel.ExcelUtil;
import com.noahpay.pay.platform.gateway.service.GrayServiceService;
import com.kalvan.admin.valid.Add;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.model.GrayService;
import com.noahpay.pay.commons.dto.platform.GrayServiceImport;
import com.kalvan.admin.log.LogType;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 灰度服务配置Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.GRAY_SERVICE)
@RestController
@RequestMapping("platform/gateway/grayservice")
@Slf4j
public class GrayServiceController extends BaseController {
    @Resource
    GrayServiceService grayServiceService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:gateway:grayservice:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<GrayService> data = grayServiceService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:gateway:grayservice:info")
    public WebResponse info(@PathVariable("id") Long id) {
        GrayService grayService = grayServiceService.findById(id);
        if (grayService == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(grayService);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出灰度节点信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("platform:gateway:grayservice:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<GrayService> data = grayServiceService.queryPage(pageInfo, params);
        String filename = "灰度服务配置";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        ExcelUtil.download(filename, response, GrayService.class, data);
    }

    /**
     * 新增灰度服务配置
     *
     * @param grayServiceImport grayServiceImport
     * @return WebResponse
     */
    @SysLog(remark = "新增灰度服务配置", type = LogType.ADD)
    @PostMapping("add")
    @Permission("platform:gateway:grayservice:add")
    public WebResponse add(@Validated({Add.class}) GrayServiceImport grayServiceImport) {
        grayServiceService.add(BeanUtil.toBean(grayServiceImport, GrayService.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 修改灰度服务配置
     *
     * @param grayServiceImport grayServiceImport
     * @return WebResponse
     */
    @SysLog(remark = "修改灰度服务配置", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("platform:gateway:grayservice:edit")
    public WebResponse edit(@Validated({Edit.class}) GrayServiceImport grayServiceImport) {
        if (grayServiceImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = grayServiceService.edit(BeanUtil.toBean(grayServiceImport, GrayService.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除灰度服务配置
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除灰度服务配置", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:gateway:grayservice:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = grayServiceService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

    @Permission("platform:gateway:grayservice:list")
    @PostMapping("updateCache")
    public WebResponse updateCache(String serviceId) {
        grayServiceService.updateCache(serviceId);
        return WebResponse.buildSuccess();
    }

    @Permission("platform:gateway:grayservice:offline")
    @PostMapping("offline")
    public WebResponse offline(Long[] ids) {
        //TODO 业务检查和处理
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess();
    }

}
