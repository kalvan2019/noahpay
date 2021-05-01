package com.noahpay.pay.job.trade.cmd;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.commons.db.trade.mapper.AsyncEventHandleMapper;
import com.noahpay.pay.commons.db.trade.model.AsyncEventHandle;
import com.noahpay.pay.job.trade.util.TransThreadMonitor;
import com.noahpay.pay.trade.constant.EventStateEnum;
import com.noahpay.pay.trade.iface.IJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 异步查询渠道状态
 *
 * @author chenliang
 */
@Slf4j
public class QuickTransSynQueryCmd implements Runnable {
    AsyncEventHandle asyncEventHandle;
    IJob iJob;
    AsyncEventHandleMapper asyncEventHandleMapper;

    public QuickTransSynQueryCmd(AsyncEventHandle asyncEventHandle, AsyncEventHandleMapper asyncEventHandleMapper, IJob iJob) {
        super();
        this.asyncEventHandle = asyncEventHandle;
        this.asyncEventHandleMapper = asyncEventHandleMapper;
        this.iJob = iJob;
        Thread.currentThread().setName(this.getClass().getSimpleName());
    }

    @Override
    public void run() {
        int row = asyncEventHandleMapper.updateStateProcess(asyncEventHandle);
        if (row != 1) {
            log.error("重复处理跳过");
            TransThreadMonitor.minusWorker(asyncEventHandle.getTransId());
            return;
        }
        log.info("异步查询渠道状态");
        Response response = iJob.synQuery(new Request().setData(asyncEventHandle.getTransId()));
        if (response.success()) {
            asyncEventHandle.setState(EventStateEnum.SUCCESS.code);
        } else {
            asyncEventHandle.setState(EventStateEnum.FAIL.code);
        }
        asyncEventHandle.setResultCode(response.getCode());
        asyncEventHandle.setResultNote(StringUtils.left(response.getMessage(), 100));
        TransThreadMonitor.minusWorker(asyncEventHandle.getTransId());
        asyncEventHandleMapper.updateStateProcessOver(asyncEventHandle);
    }
}
