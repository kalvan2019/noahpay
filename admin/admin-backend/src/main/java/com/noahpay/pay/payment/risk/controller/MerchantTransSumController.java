package com.noahpay.pay.payment.risk.controller;


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
import com.noahpay.pay.payment.risk.service.MerchantTransSumService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.risk.model.MerchantTransSum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 商户交易额度累计Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.MERCHANT_TRANS_SUM)
@RestController
@RequestMapping("payment/risk/merchanttranssum")
@Slf4j
public class MerchantTransSumController extends BaseController {
    @Resource
    MerchantTransSumService merchantTransSumService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:risk:merchanttranssum:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<MerchantTransSum> data = merchantTransSumService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 汇总查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("sum")
    @Permission("payment:risk:merchanttranssum:sum")
    public WebResponse sum(@RequestParam Map<String, Object> params) {
        Map<String, String> data = merchantTransSumService.querySum(params);
        return WebResponse.buildSuccess().putData(data);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出商户交易额度累计", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:risk:merchanttranssum:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<MerchantTransSum> data = merchantTransSumService.queryPage(pageInfo, params);
        String filename = "商户交易额度累计";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, MerchantTransSum.class, data, excludeColumnFiledNames);
    }

}
