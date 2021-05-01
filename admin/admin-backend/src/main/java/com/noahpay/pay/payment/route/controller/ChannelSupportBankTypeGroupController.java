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
import com.noahpay.pay.payment.route.service.ChannelSupportBankTypeGroupService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.channel.model.ChannelSupportBankTypeGroup;
import com.noahpay.pay.commons.dto.channel.ChannelSupportBankTypeGroupImport;
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
 * 渠道支持银行卡类型组Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.CHANNEL_SUPPORT_BANK_TYPE_GROUP)
@RestController
@RequestMapping("payment/route/channelsupportbanktypegroup")
@Slf4j
public class ChannelSupportBankTypeGroupController extends BaseController {
    @Resource
    ChannelSupportBankTypeGroupService channelSupportBankTypeGroupService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:route:channelsupportbanktypegroup:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<ChannelSupportBankTypeGroup> data = channelSupportBankTypeGroupService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:route:channelsupportbanktypegroup:info")
    public WebResponse info(@PathVariable("id") Long id) {
        ChannelSupportBankTypeGroup channelSupportBankTypeGroup = channelSupportBankTypeGroupService.findById(id);
        if (channelSupportBankTypeGroup == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(channelSupportBankTypeGroup);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出渠道支持银行卡类型组", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:route:channelsupportbanktypegroup:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<ChannelSupportBankTypeGroup> data = channelSupportBankTypeGroupService.queryPage(pageInfo, params);
        String filename = "渠道支持银行卡类型组";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, ChannelSupportBankTypeGroup.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增渠道支持银行卡类型组
     *
     * @param channelSupportBankTypeGroupImport channelSupportBankTypeGroupImport
     * @return WebResponse
     */
    @SysLog(remark = "新增渠道支持银行卡类型组", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:route:channelsupportbanktypegroup:add")
    public WebResponse add(@Validated({Add.class}) ChannelSupportBankTypeGroupImport channelSupportBankTypeGroupImport) {
        channelSupportBankTypeGroupService.add(BeanUtil.toBean(channelSupportBankTypeGroupImport, ChannelSupportBankTypeGroup.class));
        return WebResponse.buildSuccess();
    }

    /**
     * 渠道支持银行卡类型组导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:route:channelsupportbanktypegroup:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/渠道支持银行卡类型组-模板.xls", response);
    }

    /**
     * 导入渠道支持银行卡类型组
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入渠道支持银行卡类型组", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:route:channelsupportbanktypegroup:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<ChannelSupportBankTypeGroupImport> importList = ExcelUtil.importExcel(file, 1, 1, ChannelSupportBankTypeGroupImport.class);
        for (ChannelSupportBankTypeGroupImport channelSupportBankTypeGroupImport : importList) {
            channelSupportBankTypeGroupService.add(BeanUtil.toBean(channelSupportBankTypeGroupImport, ChannelSupportBankTypeGroup.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size()));
    }

    /**
     * 修改渠道支持银行卡类型组
     *
     * @param channelSupportBankTypeGroupImport channelSupportBankTypeGroupImport
     * @return WebResponse
     */
    @SysLog(remark = "修改渠道支持银行卡类型组", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:route:channelsupportbanktypegroup:edit")
    public WebResponse edit(@Validated({Edit.class}) ChannelSupportBankTypeGroupImport channelSupportBankTypeGroupImport) {
        if (channelSupportBankTypeGroupImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = channelSupportBankTypeGroupService.edit(BeanUtil.toBean(channelSupportBankTypeGroupImport, ChannelSupportBankTypeGroup.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num));
    }

    /**
     * 根据主键删除渠道支持银行卡类型组
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除渠道支持银行卡类型组", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("payment:route:channelsupportbanktypegroup:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = channelSupportBankTypeGroupService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num));
    }

}
