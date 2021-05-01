package com.noahpay.pay.payment.account.controller;


import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.client.model.WebResponse;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import com.noahpay.pay.payment.account.service.AccountTransBatchService;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.account.model.AccountTransBatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 账户记账事务控制表Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.ACCOUNT_TRANS_BATCH)
@RestController
@RequestMapping("payment/account/accounttransbatch")
@Slf4j
public class AccountTransBatchController extends BaseController {
    @Resource
    AccountTransBatchService accountTransBatchService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:account:accounttransbatch:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<AccountTransBatch> data = accountTransBatchService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

}
