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
import com.noahpay.pay.payment.cust.service.MerchantOperatorService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.cust.model.MerchantOperator;
import com.noahpay.pay.commons.dto.cust.MerchantOperatorImport;
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
 * 商户操作员表Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.MERCHANT_OPERATOR)
@RestController
@RequestMapping("payment/cust/merchantoperator")
@Slf4j
public class MerchantOperatorController extends BaseController {
    @Resource
    MerchantOperatorService merchantOperatorService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:cust:merchantoperator:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<MerchantOperator> data = merchantOperatorService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:cust:merchantoperator:info")
    public WebResponse info(@PathVariable("id") Long id) {
        MerchantOperator merchantOperator = merchantOperatorService.findById(id);
        if (merchantOperator == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(merchantOperator);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出商户操作员表", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:cust:merchantoperator:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<MerchantOperator> data = merchantOperatorService.queryPage(pageInfo, params);
        String filename = "商户操作员表";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        excludeColumnFiledNames.add("loginPwd");
        excludeColumnFiledNames.add("salt");
        ExcelUtil.download(filename, response, MerchantOperator.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增商户操作员表
     *
     * @param merchantOperatorImport merchantOperatorImport
     * @return WebResponse
     */
    @SysLog(remark = "新增商户操作员表", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:cust:merchantoperator:add")
    public WebResponse add(@Validated({Add.class}) MerchantOperatorImport merchantOperatorImport) {
        merchantOperatorService.add(BeanUtil.toBean(merchantOperatorImport, MerchantOperator.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 商户操作员表导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:cust:merchantoperator:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/商户操作员表-模板.xls", response);
    }

    /**
     * 导入商户操作员表
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入商户操作员表", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:cust:merchantoperator:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<MerchantOperatorImport> importList = ExcelUtil.importExcel(file, 1, 1, MerchantOperatorImport.class);
        for (MerchantOperatorImport merchantOperatorImport : importList) {
            merchantOperatorService.add(BeanUtil.toBean(merchantOperatorImport, MerchantOperator.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改商户操作员表
     *
     * @param merchantOperatorImport merchantOperatorImport
     * @return WebResponse
     */
    @SysLog(remark = "修改商户操作员表", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:cust:merchantoperator:edit")
    public WebResponse edit(@Validated({Edit.class}) MerchantOperatorImport merchantOperatorImport) {
        if (merchantOperatorImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = merchantOperatorService.edit(BeanUtil.toBean(merchantOperatorImport, MerchantOperator.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

}
