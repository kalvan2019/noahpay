package com.noahpay.pay.channel.iface;

import com.noahpay.pay.channel.bean.model.CheckDetailInfo;
import com.noahpay.pay.channel.bean.req.ChannelCheckRequest;
import com.noahpay.pay.channel.iface.fallback.ChannelFallbackFactory;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 对账服务
 *
 * @author chenliang
 */
@FeignClient(name = "channel",
        fallbackFactory = ChannelFallbackFactory.class
)
@RestController
public interface IChannelCheck {
    /**
     * 下载通道对账单接口
     * 供清算调用,同步返回已受理异步下载并解析为标准文档传到ftp再通知清算系统
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "check/async", method = RequestMethod.POST)
    Response asyncCheck(@RequestBody @Valid Request<ChannelCheckRequest> request);

    /**
     * 同步对账接口
     * localFilePath如果有本地文件则直接使用如果没有则下载
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "check/sync", method = RequestMethod.POST)
    Response<List<CheckDetailInfo>> syncCheck(@RequestBody @Valid Request<ChannelCheckRequest> request);
}
