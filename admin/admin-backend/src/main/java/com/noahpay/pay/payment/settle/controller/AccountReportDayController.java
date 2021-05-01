package com.noahpay.pay.payment.settle.controller;


import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.client.model.WebResponse;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import com.noahpay.pay.payment.settle.service.AccountReportDayService;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.settle.model.AccountReportDay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 账户汇总日报表Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.ACCOUNT_REPORT_DAY)
@RestController
@RequestMapping("payment/settle/accountreportday")
@Slf4j
public class AccountReportDayController extends BaseController {
    @Resource
    AccountReportDayService accountReportDayService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:settle:accountreportday:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<AccountReportDay> data = accountReportDayService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

}
