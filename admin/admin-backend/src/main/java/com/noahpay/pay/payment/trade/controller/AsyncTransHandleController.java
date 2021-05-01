package com.noahpay.pay.payment.trade.controller;


import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.client.model.WebResponse;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import com.noahpay.pay.payment.trade.service.AsyncTransHandleService;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.trade.model.AsyncTransHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 交易异步处理任务表Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.ASYNC_TRANS_HANDLE)
@RestController
@RequestMapping("payment/trade/asynctranshandle")
@Slf4j
public class AsyncTransHandleController extends BaseController {
    @Resource
    AsyncTransHandleService asyncTransHandleService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:trade:asynctranshandle:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<AsyncTransHandle> data = asyncTransHandleService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

}
