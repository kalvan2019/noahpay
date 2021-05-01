package com.noahpay.pay.payment.channel.controller;


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
import com.noahpay.pay.payment.channel.service.ChannelInfoService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.channel.model.ChannelInfo;
import com.noahpay.pay.commons.dto.channel.ChannelInfoImport;
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
 * 渠道列表Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.CHANNEL_INFO)
@RestController
@RequestMapping("payment/channel/channelinfo")
@Slf4j
public class ChannelInfoController extends BaseController {
    @Resource
    ChannelInfoService channelInfoService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:channel:channelinfo:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<ChannelInfo> data = channelInfoService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:channel:channelinfo:info")
    public WebResponse info(@PathVariable("id") Long id) {
        ChannelInfo channelInfo = channelInfoService.findById(id);
        if (channelInfo == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(channelInfo);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出渠道列表", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:channel:channelinfo:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<ChannelInfo> data = channelInfoService.queryPage(pageInfo, params);
        String filename = "渠道列表";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, ChannelInfo.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增渠道列表
     *
     * @param channelInfoImport channelInfoImport
     * @return WebResponse
     */
    @SysLog(remark = "新增渠道列表", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:channel:channelinfo:add")
    public WebResponse add(@Validated({Add.class}) ChannelInfoImport channelInfoImport) {
        channelInfoService.add(BeanUtil.toBean(channelInfoImport, ChannelInfo.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 渠道列表导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:channel:channelinfo:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/渠道列表-模板.xls", response);
    }

    /**
     * 导入渠道列表
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入渠道列表", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:channel:channelinfo:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<ChannelInfoImport> importList = ExcelUtil.importExcel(file, 1, 1, ChannelInfoImport.class);
        for (ChannelInfoImport channelInfoImport : importList) {
            channelInfoService.add(BeanUtil.toBean(channelInfoImport, ChannelInfo.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改渠道列表
     *
     * @param channelInfoImport channelInfoImport
     * @return WebResponse
     */
    @SysLog(remark = "修改渠道列表", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:channel:channelinfo:edit")
    public WebResponse edit(@Validated({Edit.class}) ChannelInfoImport channelInfoImport) {
        if (channelInfoImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = channelInfoService.edit(BeanUtil.toBean(channelInfoImport, ChannelInfo.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除渠道列表
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除渠道列表", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("payment:channel:channelinfo:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = channelInfoService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
