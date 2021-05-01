package com.noahpay.pay.payment.trade.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.admin.excel.ExcelUtil;
import com.kalvan.admin.log.LogType;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import com.noahpay.pay.payment.trade.service.TransPayBillService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.trade.model.TransPayBill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 交易订单支付明细Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.TRANS_PAY_BILL)
@RestController
@RequestMapping("payment/trade/transpaybill")
@Slf4j
public class TransPayBillController extends BaseController {
    @Resource
    TransPayBillService transPayBillService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:trade:transpaybill:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<TransPayBill> data = transPayBillService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 汇总查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("sum")
    @Permission("payment:trade:transpaybill:sum")
    public WebResponse sum(@RequestParam Map<String, Object> params) {
        Map<String, String> data = transPayBillService.querySum(params);
        return WebResponse.buildSuccess().putData(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:trade:transpaybill:info")
    public WebResponse info(@PathVariable("id") Long id) {
        TransPayBill transPayBill = transPayBillService.findById(id);
        if (transPayBill == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(transPayBill);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出交易订单支付明细", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:trade:transpaybill:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<TransPayBill> data = transPayBillService.queryPage(pageInfo, params);
        String filename = "交易订单支付明细";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        excludeColumnFiledNames.add("bankAccountExpiry");
        excludeColumnFiledNames.add("bankName");
        excludeColumnFiledNames.add("bankProtocol");
        excludeColumnFiledNames.add("certificateType");
        excludeColumnFiledNames.add("channelSendExt");
        excludeColumnFiledNames.add("channelSubMerchantNo");
        excludeColumnFiledNames.add("channelMcc");
        excludeColumnFiledNames.add("channelCity");
        excludeColumnFiledNames.add("channelBankAccountType");
        excludeColumnFiledNames.add("channelBankType");
        excludeColumnFiledNames.add("channelCertificateType");
        excludeColumnFiledNames.add("channelRecvExt");
        excludeColumnFiledNames.add("channelRecvUrl");
        ExcelUtil.download(filename, response, TransPayBill.class, data, excludeColumnFiledNames);
    }

}
