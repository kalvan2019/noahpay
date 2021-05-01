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
import com.noahpay.pay.payment.cust.service.EnterpriseInfoService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.cust.model.EnterpriseInfo;
import com.noahpay.pay.commons.dto.cust.EnterpriseInfoImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 企业客户信息Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.ENTERPRISE_INFO)
@RestController
@RequestMapping("payment/cust/enterpriseinfo")
@Slf4j
public class EnterpriseInfoController extends BaseController {
    @Resource
    EnterpriseInfoService enterpriseInfoService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:cust:enterpriseinfo:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<EnterpriseInfo> data = enterpriseInfoService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:cust:enterpriseinfo:info")
    public WebResponse info(@PathVariable("id") Long id) {
        EnterpriseInfo enterpriseInfo = enterpriseInfoService.findById(id);
        if (enterpriseInfo == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(enterpriseInfo);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出企业客户信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:cust:enterpriseinfo:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<EnterpriseInfo> data = enterpriseInfoService.queryPage(pageInfo, params);
        String filename = "企业客户信息";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, EnterpriseInfo.class, data, excludeColumnFiledNames);
    }

    /**
     * 修改企业客户信息
     *
     * @param enterpriseInfoImport enterpriseInfoImport
     * @return WebResponse
     */
    @SysLog(remark = "修改企业客户信息", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:cust:enterpriseinfo:edit")
    public WebResponse edit(@Validated({Edit.class}) EnterpriseInfoImport enterpriseInfoImport) {
        if (enterpriseInfoImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = enterpriseInfoService.edit(BeanUtil.toBean(enterpriseInfoImport, EnterpriseInfo.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

}
