package com.noahpay.pay.payment.account.controller;


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
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import com.noahpay.pay.payment.account.service.AccountFreezeBillService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.account.model.AccountFreezeBill;
import com.noahpay.pay.commons.dto.account.AccountFreezeBillImport;
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
 * 账户冻结解冻明细Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.ACCOUNT_FREEZE_BILL)
@RestController
@RequestMapping("payment/account/accountfreezebill")
@Slf4j
public class AccountFreezeBillController extends BaseController {
    @Resource
    AccountFreezeBillService accountFreezeBillService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:account:accountfreezebill:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<AccountFreezeBill> data = accountFreezeBillService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 汇总查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("sum")
    @Permission("payment:account:accountfreezebill:sum")
    public WebResponse sum(@RequestParam Map<String, Object> params) {
        Map<String, String> data = accountFreezeBillService.querySum(params);
        return WebResponse.buildSuccess().putData(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:account:accountfreezebill:info")
    public WebResponse info(@PathVariable("id") Long id) {
        AccountFreezeBill accountFreezeBill = accountFreezeBillService.findById(id);
        if (accountFreezeBill == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(accountFreezeBill);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出账户冻结解冻明细", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:account:accountfreezebill:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<AccountFreezeBill> data = accountFreezeBillService.queryPage(pageInfo, params);
        String filename = "账户冻结解冻明细";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, AccountFreezeBill.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增账户冻结解冻明细
     *
     * @param accountFreezeBillImport accountFreezeBillImport
     * @return WebResponse
     */
    @SysLog(remark = "新增账户冻结解冻明细", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:account:accountfreezebill:add")
    public WebResponse add(@Validated({Add.class}) AccountFreezeBillImport accountFreezeBillImport) {
        accountFreezeBillService.add(BeanUtil.toBean(accountFreezeBillImport, AccountFreezeBill.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 账户冻结解冻明细导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:account:accountfreezebill:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/账户冻结解冻明细-模板.xls", response);
    }

    /**
     * 导入账户冻结解冻明细
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入账户冻结解冻明细", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:account:accountfreezebill:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<AccountFreezeBillImport> importList = ExcelUtil.importExcel(file, 1, 1, AccountFreezeBillImport.class);
        for (AccountFreezeBillImport accountFreezeBillImport : importList) {
            accountFreezeBillService.add(BeanUtil.toBean(accountFreezeBillImport, AccountFreezeBill.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

}
