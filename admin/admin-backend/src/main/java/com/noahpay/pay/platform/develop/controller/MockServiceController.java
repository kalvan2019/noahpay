package com.noahpay.pay.platform.develop.controller;


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
import com.noahpay.pay.platform.develop.service.MockServiceService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.model.MockService;
import com.noahpay.pay.commons.dto.platform.MockServiceImport;
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
 * mock服务配置Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.MOCK_SERVICE)
@RestController
@RequestMapping("platform/develop/mockservice")
@Slf4j
public class MockServiceController extends BaseController {
    @Resource
    MockServiceService mockServiceService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:develop:mockservice:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<MockService> data = mockServiceService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:develop:mockservice:info")
    public WebResponse info(@PathVariable("id") Long id) {
        MockService mockService = mockServiceService.findById(id);
        if (mockService == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(mockService);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出mock服务配置", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("platform:develop:mockservice:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<MockService> data = mockServiceService.queryPage(pageInfo, params);
        String filename = "mock服务配置";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        ExcelUtil.download(filename, response, MockService.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增mock服务配置
     *
     * @param mockServiceImport mockServiceImport
     * @return WebResponse
     */
    @SysLog(remark = "新增mock服务配置", type = LogType.ADD)
    @PostMapping("add")
    @Permission("platform:develop:mockservice:add")
    public WebResponse add(@Validated({Add.class}) MockServiceImport mockServiceImport) {
        mockServiceService.add(BeanUtil.toBean(mockServiceImport, MockService.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * mock服务配置导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("platform:develop:mockservice:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/platform/mock服务配置-模板.xls", response);
    }

    /**
     * 导入mock服务配置
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入mock服务配置", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("platform:develop:mockservice:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<MockServiceImport> importList = ExcelUtil.importExcel(file, 1, 1, MockServiceImport.class);
        for (MockServiceImport mockServiceImport : importList) {
            mockServiceService.add(BeanUtil.toBean(mockServiceImport, MockService.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改mock服务配置
     *
     * @param mockServiceImport mockServiceImport
     * @return WebResponse
     */
    @SysLog(remark = "修改mock服务配置", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("platform:develop:mockservice:edit")
    public WebResponse edit(@Validated({Edit.class}) MockServiceImport mockServiceImport) {
        if (mockServiceImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = mockServiceService.edit(BeanUtil.toBean(mockServiceImport, MockService.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除mock服务配置
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除mock服务配置", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:develop:mockservice:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = mockServiceService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
