package com.noahpay.pay.platform.audit.controller;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.admin.excel.ExcelUtil;
import com.kalvan.admin.log.LogType;
import com.noahpay.pay.platform.audit.service.AuditInfoService;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.model.AuditInfo;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 审核信息Controller
 *
 * @author kalvan.tools
 */
@SysLog(group = LogGroup.AUDIT_INFO)
@RestController
@RequestMapping("platform/audit/auditinfo")
@Slf4j
public class AuditInfoController extends BaseController {
    @Resource
    AuditInfoService auditInfoService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:audit:auditinfo:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<AuditInfo> data = auditInfoService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:audit:auditinfo:info")
    public WebResponse info(@PathVariable("id") Long id) {
        AuditInfo auditInfo = auditInfoService.findById(id);
        if (auditInfo == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(auditInfo);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出审核信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("platform:audit:auditinfo:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<AuditInfo> data = auditInfoService.queryPage(pageInfo, params);
        String filename = "审核信息";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        ExcelUtil.download(filename, response, AuditInfo.class, data);
    }

    @Permission("platform:audit:auditinfo:auditing")
    @PostMapping("auditing")
    public WebResponse auditing(Long[] ids, boolean pass) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int row=auditInfoService.auditing(ids, pass);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_AUDITING.formatMessage(row));
    }
}
