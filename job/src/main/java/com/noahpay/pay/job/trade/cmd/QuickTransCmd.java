package com.noahpay.pay.job.trade.cmd;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.commons.db.trade.mapper.AsyncTransHandleMapper;
import com.noahpay.pay.commons.db.trade.model.AsyncTransHandle;
import com.noahpay.pay.job.trade.util.TransThreadMonitor;
import com.noahpay.pay.trade.constant.EventStateEnum;
import com.noahpay.pay.trade.iface.IJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 异步处理交易
 *
 * @author chenliang
 */
@Slf4j
public class QuickTransCmd implements Runnable {
    private final AsyncTransHandle asyncTransHandle;
    IJob iJob;
    AsyncTransHandleMapper asyncTransHandleMapper;

    public QuickTransCmd(AsyncTransHandle asyncTransHandle, IJob iJob, AsyncTransHandleMapper asyncTransHandleMapper) {
        super();
        this.asyncTransHandle = asyncTransHandle;
        this.iJob = iJob;
        this.asyncTransHandleMapper = asyncTransHandleMapper;
        Thread.currentThread().setName(this.getClass().getSimpleName());
    }

    @Override
    public void run() {
        int row = asyncTransHandleMapper.updateStateProcess(asyncTransHandle.getId());
        if (row != 1) {
            log.error("重复处理跳过");
            TransThreadMonitor.minusWorker(asyncTransHandle.getTransId());
            return;
        }
        log.info("异步处理交易");
        Response response = iJob.asyncTrans(new Request().setData(asyncTransHandle.getTransId()));
        if (response.success()) {
            asyncTransHandle.setState(EventStateEnum.SUCCESS.code);
        } else {
            asyncTransHandle.setState(EventStateEnum.FAIL.code);
        }
        asyncTransHandle.setResultCode(response.getCode());
        asyncTransHandle.setResultNote(StringUtils.left(response.getMessage(), 100));
        TransThreadMonitor.minusWorker(asyncTransHandle.getTransId());
        asyncTransHandleMapper.updateStateEnd(asyncTransHandle);
    }
}
