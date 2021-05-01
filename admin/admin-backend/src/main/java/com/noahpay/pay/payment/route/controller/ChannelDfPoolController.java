package com.noahpay.pay.payment.route.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.kalvan.admin.annotation.ParamsDecrypted;
import com.kalvan.admin.annotation.Permission;
import com.kalvan.admin.annotation.SysLog;
import com.kalvan.admin.excel.ExcelUtil;
import com.kalvan.admin.log.LogType;
import com.kalvan.admin.valid.Add;
import com.kalvan.admin.valid.Edit;
import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.WebResponse;
import com.kalvan.db.mybatis.PageInfo;
import com.kalvan.web.controller.BaseController;
import com.noahpay.pay.payment.route.service.ChannelDfPoolService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.channel.model.ChannelDfPool;
import com.noahpay.pay.commons.dto.channel.ChannelDfPoolImport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 渠道结算商户绑定Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.CHANNEL_DF_POOL)
@RestController
@RequestMapping("payment/route/channeldfpool")
@Slf4j
public class ChannelDfPoolController extends BaseController {
    @Resource
    ChannelDfPoolService channelDfPoolService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:route:channeldfpool:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<ChannelDfPool> data = channelDfPoolService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 汇总查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("sum")
    @Permission("payment:route:channeldfpool:sum")
    public WebResponse sum(@RequestParam Map<String, Object> params) {
        Map<String, String> data = channelDfPoolService.querySum(params);
        return WebResponse.buildSuccess().putData(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:route:channeldfpool:info")
    public WebResponse info(@PathVariable("id") Long id) {
        ChannelDfPool channelDfPool = channelDfPoolService.findById(id);
        if (channelDfPool == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(channelDfPool);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出渠道结算商户绑定", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:route:channeldfpool:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<ChannelDfPool> data = channelDfPoolService.queryPage(pageInfo, params);
        String filename = "渠道结算商户绑定";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, ChannelDfPool.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增渠道结算商户绑定
     *
     * @param channelDfPoolImport channelDfPoolImport
     * @return WebResponse
     */
    @SysLog(remark = "新增渠道结算商户绑定", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:route:channeldfpool:add")
    public WebResponse add(@Validated({Add.class}) ChannelDfPoolImport channelDfPoolImport) {
        channelDfPoolService.add(BeanUtil.toBean(channelDfPoolImport, ChannelDfPool.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 渠道结算商户绑定导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:route:channeldfpool:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/渠道结算商户绑定-模板.xls", response);
    }

    /**
     * 导入渠道结算商户绑定
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入渠道结算商户绑定", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:route:channeldfpool:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<ChannelDfPoolImport> importList = ExcelUtil.importExcel(file, 1, 1, ChannelDfPoolImport.class);
        for (ChannelDfPoolImport channelDfPoolImport : importList) {
            channelDfPoolService.add(BeanUtil.toBean(channelDfPoolImport, ChannelDfPool.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改渠道结算商户绑定
     *
     * @param channelDfPoolImport channelDfPoolImport
     * @return WebResponse
     */
    @SysLog(remark = "修改渠道结算商户绑定", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:route:channeldfpool:edit")
    public WebResponse edit(@Validated({Edit.class}) ChannelDfPoolImport channelDfPoolImport) {
        if (channelDfPoolImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = channelDfPoolService.edit(BeanUtil.toBean(channelDfPoolImport, ChannelDfPool.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除渠道结算商户绑定
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除渠道结算商户绑定", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("payment:route:channeldfpool:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = channelDfPoolService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
