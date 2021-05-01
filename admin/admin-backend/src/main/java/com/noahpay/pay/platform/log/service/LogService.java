package com.noahpay.pay.platform.log.service;

import cn.hutool.core.date.DateUtil;
import com.kalvan.client.exception.BizException;
import com.noahpay.pay.platform.audit.service.BaseAuditService;
import com.noahpay.pay.commons.constant.AdminReturnCode;
import com.noahpay.pay.commons.db.platform.mapper.LogMapper;
import com.noahpay.pay.commons.db.platform.model.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 操作日志记录Service实现类
 *
 * @author kalvan
 * @date 2019-06-01 02:29:27
 */
@Service
@Slf4j
public class LogService extends BaseAuditService<Log> {
    @Resource
    protected LogMapper mapper;
    int between = 31;

    @Override
    public void processParams(Map<String, Object> params) {
        //  查询和下载的条件检查
        String logTime = String.valueOf(params.get("logTime"));
        String logTimeBegin = null;
        String logTimeEnd = null;
        try {
            if (StringUtils.isNotBlank(logTime)) {
                String[] times = logTime.split(",");
                logTimeBegin = StringUtils.rightPad(times[0], 19, " 00:00:00");
                params.put("logTimeBegin", times[0]);
                if (times.length > 1) {
                    logTimeEnd = StringUtils.rightPad(times[1], 19, " 00:00:00");
                    params.put("logTimeEnd", logTimeEnd);
                }
            }
        } catch (Throwable t) {
            log.error("参数解析异常", t);
            throw new BizException(AdminReturnCode.ERROR);
        }
        if (StringUtils.isBlank(logTimeBegin)) {
            throw new BizException(AdminReturnCode.PARAM_NOT_NULL.formatMessage("操作时间"));
        }
        if (StringUtils.isBlank(logTimeEnd)) {
            params.put("logTimeEnd", DateUtil.formatDateTime(DateUtil.beginOfDay(DateUtil.date())));
        }
        // 检查日期间隔
        if (DateUtil.betweenDay(DateUtil.parseDateTime(logTimeBegin), DateUtil.parseDateTime(logTimeEnd), true) > between) {
            throw new BizException(AdminReturnCode.PARAM_REGION_OVER.formatMessage("操作时间", between));
        }
    }
}
