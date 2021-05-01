package com.noahpay.pay.payment.cust.controller;


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
import com.noahpay.pay.payment.cust.service.SubMerchantInfoService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.cust.model.SubMerchantInfo;
import com.noahpay.pay.commons.dto.cust.SubMerchantInfoImport;
import lombok.extern.slf4j.Slf4j;
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
 * 子商户信息Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.SUB_MERCHANT_INFO)
@RestController
@RequestMapping("payment/cust/submerchantinfo")
@Slf4j
public class SubMerchantInfoController extends BaseController {
    @Resource
    SubMerchantInfoService subMerchantInfoService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:cust:submerchantinfo:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<SubMerchantInfo> data = subMerchantInfoService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:cust:submerchantinfo:info")
    public WebResponse info(@PathVariable("id") Long id) {
        SubMerchantInfo subMerchantInfo = subMerchantInfoService.findById(id);
        if (subMerchantInfo == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(subMerchantInfo);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出子商户信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:cust:submerchantinfo:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<SubMerchantInfo> data = subMerchantInfoService.queryPage(pageInfo, params);
        String filename = "子商户信息";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, SubMerchantInfo.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增子商户信息
     *
     * @param subMerchantInfoImport subMerchantInfoImport
     * @return WebResponse
     */
    @SysLog(remark = "新增子商户信息", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:cust:submerchantinfo:add")
    public WebResponse add(@Validated({Add.class}) SubMerchantInfoImport subMerchantInfoImport) {
        subMerchantInfoService.add(BeanUtil.toBean(subMerchantInfoImport, SubMerchantInfo.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 子商户信息导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:cust:submerchantinfo:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/子商户信息-模板.xls", response);
    }

    /**
     * 导入子商户信息
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入子商户信息", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:cust:submerchantinfo:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<SubMerchantInfoImport> importList = ExcelUtil.importExcel(file, 1, 1, SubMerchantInfoImport.class);
        for (SubMerchantInfoImport subMerchantInfoImport : importList) {
            subMerchantInfoService.add(BeanUtil.toBean(subMerchantInfoImport, SubMerchantInfo.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改子商户信息
     *
     * @param subMerchantInfoImport subMerchantInfoImport
     * @return WebResponse
     */
    @SysLog(remark = "修改子商户信息", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:cust:submerchantinfo:edit")
    public WebResponse edit(@Validated({Edit.class}) SubMerchantInfoImport subMerchantInfoImport) {
        if (subMerchantInfoImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = subMerchantInfoService.edit(BeanUtil.toBean(subMerchantInfoImport, SubMerchantInfo.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

}
