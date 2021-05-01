package com.noahpay.pay.payment.cust.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.admin.excel.ExcelUtil;
import com.kalvan.admin.log.LogType;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import com.noahpay.pay.payment.cust.service.CustInfoService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.cust.model.CustInfo;
import com.noahpay.pay.commons.dto.cust.CustInfoImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 客户信息Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.CUST_INFO)
@RestController
@RequestMapping("payment/cust/custinfo")
@Slf4j
public class CustInfoController extends BaseController {
    @Resource
    CustInfoService custInfoService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:cust:custinfo:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<CustInfo> data = custInfoService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:cust:custinfo:info")
    public WebResponse info(@PathVariable("id") Long id) {
        CustInfo custInfo = custInfoService.findById(id);
        if (custInfo == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(custInfo);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出客户信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:cust:custinfo:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<CustInfo> data = custInfoService.queryPage(pageInfo, params);
        String filename = "客户信息";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, CustInfo.class, data, excludeColumnFiledNames);
    }

    /**
     * 修改客户信息
     *
     * @param custInfoImport custInfoImport
     * @return WebResponse
     */
    @SysLog(remark = "修改客户信息", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:cust:custinfo:edit")
    public WebResponse edit(@Validated({Edit.class}) CustInfoImport custInfoImport) {
        if (custInfoImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = custInfoService.edit(BeanUtil.toBean(custInfoImport, CustInfo.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

}
