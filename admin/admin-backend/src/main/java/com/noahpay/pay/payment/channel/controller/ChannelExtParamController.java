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
import com.noahpay.pay.payment.channel.service.ChannelExtParamService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.channel.model.ChannelExtParam;
import com.noahpay.pay.commons.dto.channel.ChannelExtParamImport;
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
 * 渠道扩展参数Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.CHANNEL_EXT_PARAM)
@RestController
@RequestMapping("payment/channel/channelextparam")
@Slf4j
public class ChannelExtParamController extends BaseController {
    @Resource
    ChannelExtParamService channelExtParamService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:channel:channelextparam:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<ChannelExtParam> data = channelExtParamService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:channel:channelextparam:info")
    public WebResponse info(@PathVariable("id") Long id) {
        ChannelExtParam channelExtParam = channelExtParamService.findById(id);
        if (channelExtParam == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(channelExtParam);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出渠道扩展参数", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:channel:channelextparam:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<ChannelExtParam> data = channelExtParamService.queryPage(pageInfo, params);
        String filename = "渠道扩展参数";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, ChannelExtParam.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增渠道扩展参数
     *
     * @param channelExtParamImport channelExtParamImport
     * @return WebResponse
     */
    @SysLog(remark = "新增渠道扩展参数", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:channel:channelextparam:add")
    public WebResponse add(@Validated({Add.class}) ChannelExtParamImport channelExtParamImport) {
        channelExtParamService.add(BeanUtil.toBean(channelExtParamImport, ChannelExtParam.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 渠道扩展参数导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:channel:channelextparam:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/渠道扩展参数-模板.xls", response);
    }

    /**
     * 导入渠道扩展参数
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入渠道扩展参数", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:channel:channelextparam:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<ChannelExtParamImport> importList = ExcelUtil.importExcel(file, 1, 1, ChannelExtParamImport.class);
        for (ChannelExtParamImport channelExtParamImport : importList) {
            channelExtParamService.add(BeanUtil.toBean(channelExtParamImport, ChannelExtParam.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改渠道扩展参数
     *
     * @param channelExtParamImport channelExtParamImport
     * @return WebResponse
     */
    @SysLog(remark = "修改渠道扩展参数", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:channel:channelextparam:edit")
    public WebResponse edit(@Validated({Edit.class}) ChannelExtParamImport channelExtParamImport) {
        if (channelExtParamImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = channelExtParamService.edit(BeanUtil.toBean(channelExtParamImport, ChannelExtParam.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除渠道扩展参数
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除渠道扩展参数", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("payment:channel:channelextparam:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = channelExtParamService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
