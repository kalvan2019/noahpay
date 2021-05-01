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
import com.noahpay.pay.payment.cust.service.MerchantProductTransService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.cust.model.MerchantProductTrans;
import com.noahpay.pay.commons.dto.cust.MerchantProductTransImport;
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
 * 商户交易业务入网Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.MERCHANT_PRODUCT_TRANS)
@RestController
@RequestMapping("payment/cust/merchantproducttrans")
@Slf4j
public class MerchantProductTransController extends BaseController {
    @Resource
    MerchantProductTransService merchantProductTransService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:cust:merchantproducttrans:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<MerchantProductTrans> data = merchantProductTransService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:cust:merchantproducttrans:info")
    public WebResponse info(@PathVariable("id") Long id) {
        MerchantProductTrans merchantProductTrans = merchantProductTransService.findById(id);
        if (merchantProductTrans == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(merchantProductTrans);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出商户交易业务入网", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:cust:merchantproducttrans:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<MerchantProductTrans> data = merchantProductTransService.queryPage(pageInfo, params);
        String filename = "商户交易业务入网";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, MerchantProductTrans.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增商户交易业务入网
     *
     * @param merchantProductTransImport merchantProductTransImport
     * @return WebResponse
     */
    @SysLog(remark = "新增商户交易业务入网", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:cust:merchantproducttrans:add")
    public WebResponse add(@Validated({Add.class}) MerchantProductTransImport merchantProductTransImport) {
        merchantProductTransService.add(BeanUtil.toBean(merchantProductTransImport, MerchantProductTrans.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 商户交易业务入网导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:cust:merchantproducttrans:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/商户交易业务入网-模板.xls", response);
    }

    /**
     * 导入商户交易业务入网
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入商户交易业务入网", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:cust:merchantproducttrans:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<MerchantProductTransImport> importList = ExcelUtil.importExcel(file, 1, 1, MerchantProductTransImport.class);
        for (MerchantProductTransImport merchantProductTransImport : importList) {
            merchantProductTransService.add(BeanUtil.toBean(merchantProductTransImport, MerchantProductTrans.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改商户交易业务入网
     *
     * @param merchantProductTransImport merchantProductTransImport
     * @return WebResponse
     */
    @SysLog(remark = "修改商户交易业务入网", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:cust:merchantproducttrans:edit")
    public WebResponse edit(@Validated({Edit.class}) MerchantProductTransImport merchantProductTransImport) {
        if (merchantProductTransImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = merchantProductTransService.edit(BeanUtil.toBean(merchantProductTransImport, MerchantProductTrans.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除商户交易业务入网
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除商户交易业务入网", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("payment:cust:merchantproducttrans:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = merchantProductTransService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
