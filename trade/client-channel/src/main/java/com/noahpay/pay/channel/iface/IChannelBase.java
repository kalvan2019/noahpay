package com.noahpay.pay.channel.iface;

import com.noahpay.pay.channel.bean.req.ChannelCallbackRequest;
import com.noahpay.pay.channel.bean.req.ChannelConvertRequest;
import com.noahpay.pay.channel.bean.res.ChannelCallbackResponse;
import com.noahpay.pay.channel.bean.res.ChannelConvertResponse;
import com.noahpay.pay.channel.iface.fallback.ChannelFallbackFactory;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 基础服务
 *
 * @author chenliang
 */
@FeignClient(name = "channel",
        fallbackFactory = ChannelFallbackFactory.class
)
@RestController
public interface IChannelBase {
    /**
     * 通道数据转换
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "paramConvert", method = RequestMethod.POST)
    Response<ChannelConvertResponse> channelParamConvert(@RequestBody @Valid Request<ChannelConvertRequest> request);

    /**
     * 通道异步回调
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    Response<ChannelCallbackResponse> callback(@RequestBody @Valid Request<ChannelCallbackRequest> request);
}
