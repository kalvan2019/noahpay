package com.noahpay.pay.platform.gateway.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.excel.ExcelUtil;
import com.noahpay.pay.platform.gateway.service.GrayRuleService;
import com.kalvan.admin.valid.Add;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.platform.model.GrayRule;
import com.noahpay.pay.commons.dto.platform.GrayRuleImport;
import com.kalvan.admin.log.LogType;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 灰度规则配置Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.GRAY_RULE)
@RestController
@RequestMapping("platform/gateway/grayrule")
@Slf4j
public class GrayRuleController extends BaseController {
    @Resource
    GrayRuleService grayRuleService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("platform:gateway:grayrule:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<GrayRule> data = grayRuleService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("platform:gateway:grayrule:info")
    public WebResponse info(@PathVariable("id") Long id) {
        GrayRule grayRule = grayRuleService.findById(id);
        if (grayRule == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(grayRule);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出灰度规则信息", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("platform:gateway:grayrule:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<GrayRule> data = grayRuleService.queryPage(pageInfo, params);
        String filename = "灰度规则配置";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        ExcelUtil.download(filename, response, GrayRule.class, data);
    }

    /**
     * 新增灰度规则配置
     *
     * @param grayRuleImport grayRuleImport
     * @return WebResponse
     */
    @SysLog(remark = "新增灰度规则配置", type = LogType.ADD)
    @PostMapping("add")
    @Permission("platform:gateway:grayrule:add")
    public WebResponse add(@Validated({Add.class}) GrayRuleImport grayRuleImport) {
        grayRuleService.add(BeanUtil.toBean(grayRuleImport, GrayRule.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 修改灰度规则配置
     *
     * @param grayRuleImport grayRuleImport
     * @return WebResponse
     */
    @SysLog(remark = "修改灰度规则配置", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("platform:gateway:grayrule:edit")
    public WebResponse edit(@Validated({Edit.class}) GrayRuleImport grayRuleImport) {
        if (grayRuleImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = grayRuleService.edit(BeanUtil.toBean(grayRuleImport, GrayRule.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除灰度规则配置
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除灰度规则配置", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("platform:gateway:grayrule:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = grayRuleService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
