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
import com.noahpay.pay.payment.route.service.ChannelMultiMerchantService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.constant.LogGroup;
import com.noahpay.pay.commons.db.channel.model.ChannelMultiMerchant;
import com.noahpay.pay.commons.dto.channel.ChannelMultiMerchantImport;
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
 * 渠道收款商户绑定Controller
 *
 * @author kalvan
 */
@SysLog(group = LogGroup.CHANNEL_MULTI_MERCHANT)
@RestController
@RequestMapping("payment/route/channelmultimerchant")
@Slf4j
public class ChannelMultiMerchantController extends BaseController {
    @Resource
    ChannelMultiMerchantService channelMultiMerchantService;

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return WebResponse
     */
    @PostMapping("list")
    @Permission("payment:route:channelmultimerchant:list")
    public WebResponse list(PageInfo pageInfo, @RequestParam Map<String, Object> params) {
        Page<ChannelMultiMerchant> data = channelMultiMerchantService.queryPage(pageInfo, params);
        return WebResponse.buildSuccess().putPage(data);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return WebResponse
     */
    @GetMapping("info/{id}")
    @Permission("payment:route:channelmultimerchant:info")
    public WebResponse info(@PathVariable("id") Long id) {
        ChannelMultiMerchant channelMultiMerchant = channelMultiMerchantService.findById(id);
        if (channelMultiMerchant == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        return WebResponse.buildSuccess().putData(channelMultiMerchant);
    }

    private static final int DOWNLOAD_MAX_COUNT = 20000;

    /**
     * 下载
     *
     * @param params   查询参数
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @SysLog(remark = "导出渠道收款商户绑定", type = LogType.EXPORT)
    @PostMapping("download")
    @Permission("payment:route:channelmultimerchant:download")
    public void download(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPage(1);
        pageInfo.setLimit(DOWNLOAD_MAX_COUNT);
        Page<ChannelMultiMerchant> data = channelMultiMerchantService.queryPage(pageInfo, params);
        String filename = "渠道收款商户绑定";
        if (data.getTotal() > DOWNLOAD_MAX_COUNT) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("下载条数", DOWNLOAD_MAX_COUNT));
        }
        filename = filename + DatePattern.PURE_DATETIME_MS_FORMAT.format(DateUtil.date());
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        ExcelUtil.download(filename, response, ChannelMultiMerchant.class, data, excludeColumnFiledNames);
    }

    /**
     * 新增渠道收款商户绑定
     *
     * @param channelMultiMerchantImport channelMultiMerchantImport
     * @return WebResponse
     */
    @SysLog(remark = "新增渠道收款商户绑定", type = LogType.ADD)
    @PostMapping("add")
    @Permission("payment:route:channelmultimerchant:add")
    public WebResponse add(@Validated({Add.class}) ChannelMultiMerchantImport channelMultiMerchantImport) {
        channelMultiMerchantService.add(BeanUtil.toBean(channelMultiMerchantImport, ChannelMultiMerchant.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(1, "待审核"));
    }

    /**
     * 渠道收款商户绑定导入模板下载
     *
     * @param response response
     * @throws Exception 抛出异常拦截器统一处理
     */
    @GetMapping("downloadTemplate")
    @Permission("payment:route:channelmultimerchant:upload")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        ExcelUtil.downloadTemplate(templateRootPath + "com/kalvan/payment/渠道收款商户绑定-模板.xls", response);
    }

    /**
     * 导入渠道收款商户绑定
     *
     * @param file file
     * @return WebResponse
     */
    @SysLog(remark = "导入渠道收款商户绑定", type = LogType.IMPORT)
    @PostMapping("upload")
    @Permission("payment:route:channelmultimerchant:upload")
    @ParamsDecrypted(required = false)
    public WebResponse upload(@RequestParam MultipartFile file) throws Exception {
        List<ChannelMultiMerchantImport> importList = ExcelUtil.importExcel(file, 1, 1, ChannelMultiMerchantImport.class);
        for (ChannelMultiMerchantImport channelMultiMerchantImport : importList) {
            channelMultiMerchantService.add(BeanUtil.toBean(channelMultiMerchantImport, ChannelMultiMerchant.class));
        }
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_IMPORT.formatMessage(importList.size(), "待审核"));
    }

    /**
     * 修改渠道收款商户绑定
     *
     * @param channelMultiMerchantImport channelMultiMerchantImport
     * @return WebResponse
     */
    @SysLog(remark = "修改渠道收款商户绑定", type = LogType.EDIT)
    @PostMapping("edit")
    @Permission("payment:route:channelmultimerchant:edit")
    public WebResponse edit(@Validated({Edit.class}) ChannelMultiMerchantImport channelMultiMerchantImport) {
        if (channelMultiMerchantImport.getId() == null) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = channelMultiMerchantService.edit(BeanUtil.toBean(channelMultiMerchantImport, ChannelMultiMerchant.class));
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_UPDATE.formatMessage(num, "待审核"));
    }

    /**
     * 根据主键删除渠道收款商户绑定
     *
     * @param ids ids
     * @return WebResponse
     */
    @SysLog(remark = "删除渠道收款商户绑定", type = LogType.DELETE)
    @PostMapping("delete")
    @Permission("payment:route:channelmultimerchant:delete")
    public WebResponse delete(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new BizException(AdminReturnCode.REQUEST_ILLEGAL);
        }
        int num = channelMultiMerchantService.deleteBatch(ids);
        return WebResponse.buildResult(AdminReturnCode.SUCCESS_DELETE.formatMessage(num, "待审核"));
    }

}
