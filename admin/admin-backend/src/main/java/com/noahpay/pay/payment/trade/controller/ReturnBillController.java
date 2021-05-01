package com.noahpay.pay.payment.trade.controller;


import cn.hutool.core.bean.BeanUtil;
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
import com.noahpay.pay.payment.trade.service.ReturnBillService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.trade.model.ReturnBill;
import com.noahpay.pay.commons.dto.trade.ReturnBillImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 退票流水Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.RETURN_BILL)
@RestController
@RequestMapping("payment/trade/returnbill")
@Slf4j
public class ReturnBillController extends BaseController {
    @Resource
    ReturnBillService returnBillService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:trade:returnbill:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<ReturnBill> data = returnBillService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:trade:returnbill:info")
    public WebResponse info(@PathVariable("id") Long id) {
        ReturnBill returnBill = returnBillService.findById(id);
        if (returnBill == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(returnBill);
    }

    /**
     * 新增退票流水
     *
     * @param returnBillImport returnBillImport
     * @return WebResponse
     */
    @SysLog(remark = "新增退票流水", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:trade:returnbill:add")
    public WebResponse add(@Validated({Add.class}) ReturnBillImport returnBillImport) {
        returnBillService.add(BeanUtil.toBean(returnBillImport, ReturnBill.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 退票流水导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:trade:returnbill:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/退票流水-模板.xls", response);
    }

    /**
     * 导入退票流水
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入退票流水", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:trade:returnbill:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<ReturnBillImport> importList = ExcelUtil.importExcel(file, 1, 1, ReturnBillImport.class);
        for (ReturnBillImport returnBillImport : importList) {
            returnBillService.add(BeanUtil.toBean(returnBillImport, ReturnBill.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

}
