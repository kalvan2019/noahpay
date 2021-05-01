package com.noahpay.pay.payment.fee.controller;


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
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.payment.fee.service.FeeSegmentRuleService;
import com.noahpay.pay.commons.db.cust.model.FeeSegmentRule;
import com.noahpay.pay.commons.dto.cust.FeeSegmentRuleImport;
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
 * 分段计费规则配置Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.FEE_SEGMENT_RULE)
@RestController
@RequestMapping("payment/fee/feesegmentrule")
@Slf4j
public class FeeSegmentRuleController extends BaseController {
    @Resource
    FeeSegmentRuleService feeSegmentRuleService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:fee:feesegmentrule:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<FeeSegmentRule> data = feeSegmentRuleService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:fee:feesegmentrule:info")
    public WebResponse info(@PathVariable("id") Long id) {
        FeeSegmentRule feeSegmentRule = feeSegmentRuleService.findById(id);
        if (feeSegmentRule == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(feeSegmentRule);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出分段计费规则配置", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:fee:feesegmentrule:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<FeeSegmentRule> data = feeSegmentRuleService.queryPage(pageInfo, params);
        String filename = "分段计费规则配置";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, FeeSegmentRule.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增分段计费规则配置
     *
     * @param feeSegmentRuleImport feeSegmentRuleImport
     * @return WebResponse
     */
    @SysLog(remark = "新增分段计费规则配置", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:fee:feesegmentrule:add")
    public WebResponse add(@Validated({Add.class}) FeeSegmentRuleImport feeSegmentRuleImport) {
        feeSegmentRuleService.add(BeanUtil.toBean(feeSegmentRuleImport, FeeSegmentRule.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 分段计费规则配置导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:fee:feesegmentrule:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/分段计费规则配置-模板.xls", response);
    }

    /**
     * 导入分段计费规则配置
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入分段计费规则配置", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:fee:feesegmentrule:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<FeeSegmentRuleImport> importList = ExcelUtil.importExcel(file, 1, 1, FeeSegmentRuleImport.class);
        for (FeeSegmentRuleImport feeSegmentRuleImport : importList) {
            feeSegmentRuleService.add(BeanUtil.toBean(feeSegmentRuleImport, FeeSegmentRule.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改分段计费规则配置
     *
     * @param feeSegmentRuleImport feeSegmentRuleImport
     * @return WebResponse
     */
    @SysLog(remark = "修改分段计费规则配置", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:fee:feesegmentrule:edit")
    public WebResponse edit(@Validated({Edit.class}) FeeSegmentRuleImport feeSegmentRuleImport) {
        if (feeSegmentRuleImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = feeSegmentRuleService.edit(BeanUtil.toBean(feeSegmentRuleImport, FeeSegmentRule.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除分段计费规则配置
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除分段计费规则配置", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("payment:fee:feesegmentrule:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = feeSegmentRuleService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
