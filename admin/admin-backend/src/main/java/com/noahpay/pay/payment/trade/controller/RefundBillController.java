package com.noahpay.pay.payment.trade.controller;


import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.client.model.WebResponse;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import com.noahpay.pay.payment.trade.service.RefundBillService;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.trade.model.RefundBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 退款交易流水Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.REFUND_BILL)
@RestController
@RequestMapping("payment/trade/refundbill")
@Slf4j
public class RefundBillController extends BaseController {
    @Resource
    RefundBillService refundBillService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:trade:refundbill:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<RefundBill> data = refundBillService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

}
