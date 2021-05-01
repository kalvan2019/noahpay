package com.noahpay.pay.platform.develop.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.ParamsDecrypted;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.admin.excel.ExcelUtil;
import com.noahpay.pay.platform.develop.service.DictService;
import com.kalvan.admin.log.LogType;
import com.kalvan.admin.valid.Add;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.mapper.DictMapper;
import com.noahpay.pay.commons.db.platform.model.Dict;
import com.noahpay.pay.commons.dto.platform.DictImport;
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
 * 数据字典配置Controller
 *
 * @author kalvan
 * @date 2020-08-23 15:07:36
 */
@SysLog(group = LogGroup.DICT)
@RestController
@RequestMapping("platform/develop/dict")
@Slf4j
public class DictController extends BaseController {
    private static final int DOWNLOAD_MAX_COUNT = 20000;
    @Resource
    DictService dictService;
    @Resource
    DictMapper dictMapper;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:develop:dict:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page data = dictService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:develop:dict:info")
    public WebResponse info(@PathVariable("id") Long id) {
        Dict dict = dictService.findById(id);
        if (dict == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(dict);
    }

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出字典信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("platform:develop:dict:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page data = dictService.queryPage(pageInfo, params);
        String filename = "数据字典配置";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        ExcelUtil.download(filename, response, Dict.class, data);
    }

    /**
     * 新增数据字典配置
     *
     * @param dictImport dictImport
     * @return WebResponse
     */
    @SysLog(remark = "新增数据字典配置", type = LogType.ADD)
    @PostMapping("add")
    @Permission("platform:develop:dict:add")
    public WebResponse add(@Validated({Add.class}) DictImport dictImport) {
        dictService.add(BeanUtil.toBean(dictImport, Dict.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 数据字典配置导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("platform:develop:dict:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/platform/数据字典配置-模板.xls", response);
    }

    /**
     * 导入数据字典配置
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入数据字典配置", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("platform:develop:dict:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<DictImport> importList = ExcelUtil.importExcel(file, 1, 1, DictImport.class);
        for (DictImport dictImport : importList) {
            dictService.add(BeanUtil.toBean(dictImport, Dict.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改数据字典配置
     *
     * @param dictImport dictImport
     * @return WebResponse
     */
    @SysLog(remark = "修改数据字典配置", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("platform:develop:dict:edit")
    public WebResponse edit(@Validated({Edit.class}) DictImport dictImport) {
        if (dictImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = dictService.edit(BeanUtil.toBean(dictImport, Dict.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除数据字典配置
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除数据字典配置", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:develop:dict:delete")
    public WebResponse delete(Integer[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = dictService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

    @Permission("platform:develop:dict:updateCache")
    @PostMapping("updateCache")
    public WebResponse updateCache(String dictType) {
        dictService.updateCache(dictType);
        return WebResponse.buildSuccess();
    }

    @PostMapping("allType")
    public WebResponse allType() {
        return WebResponse.buildSuccess().putData(dictMapper.selectAllType());
    }

}
