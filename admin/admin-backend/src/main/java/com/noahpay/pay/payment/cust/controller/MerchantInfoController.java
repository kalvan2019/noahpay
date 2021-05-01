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
import com.noahpay.pay.payment.cust.service.MerchantInfoService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.cust.model.MerchantInfo;
import com.noahpay.pay.commons.dto.cust.MerchantInfoImport;
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
 * 商户信息Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.MERCHANT_INFO)
@RestController
@RequestMapping("payment/cust/merchantinfo")
@Slf4j
public class MerchantInfoController extends BaseController {
    @Resource
    MerchantInfoService merchantInfoService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:cust:merchantinfo:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<MerchantInfo> data = merchantInfoService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:cust:merchantinfo:info")
    public WebResponse info(@PathVariable("id") Long id) {
        MerchantInfo merchantInfo = merchantInfoService.findById(id);
        if (merchantInfo == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(merchantInfo);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出商户信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:cust:merchantinfo:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<MerchantInfo> data = merchantInfoService.queryPage(pageInfo, params);
        String filename = "商户信息";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        excludeColumnFiledNames.add("loginPassword");
        excludeColumnFiledNames.add("payPassword");
        excludeColumnFiledNames.add("salt");
        ExcelUtil.download(filename, response, MerchantInfo.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增商户信息
     *
     * @param merchantInfoImport merchantInfoImport
     * @return WebResponse
     */
    @SysLog(remark = "新增商户信息", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:cust:merchantinfo:add")
    public WebResponse add(@Validated({Add.class}) MerchantInfoImport merchantInfoImport) {
        merchantInfoService.add(BeanUtil.toBean(merchantInfoImport, MerchantInfo.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 商户信息导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:cust:merchantinfo:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/商户信息-模板.xls", response);
    }

    /**
     * 导入商户信息
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入商户信息", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:cust:merchantinfo:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<MerchantInfoImport> importList = ExcelUtil.importExcel(file, 1, 1, MerchantInfoImport.class);
        for (MerchantInfoImport merchantInfoImport : importList) {
            merchantInfoService.add(BeanUtil.toBean(merchantInfoImport, MerchantInfo.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改商户信息
     *
     * @param merchantInfoImport merchantInfoImport
     * @return WebResponse
     */
    @SysLog(remark = "修改商户信息", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:cust:merchantinfo:edit")
    public WebResponse edit(@Validated({Edit.class}) MerchantInfoImport merchantInfoImport) {
        if (merchantInfoImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = merchantInfoService.edit(BeanUtil.toBean(merchantInfoImport, MerchantInfo.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除商户信息
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除商户信息", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("payment:cust:merchantinfo:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = merchantInfoService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
