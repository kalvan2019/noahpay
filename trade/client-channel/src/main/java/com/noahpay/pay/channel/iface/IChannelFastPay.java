package com.noahpay.pay.channel.iface;

import com.noahpay.pay.channel.bean.req.ChannelTransConfirmRequest;
import com.noahpay.pay.channel.bean.req.ChannelTransQueryRequest;
import com.noahpay.pay.channel.bean.req.ChannelTransRequest;
import com.noahpay.pay.channel.bean.res.ChannelTransResponse;
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
 * 协议支付
 *
 * @author chenliang
 */
@FeignClient(name = "channel",
        fallbackFactory = ChannelFallbackFactory.class
)
@RestController
public interface IChannelFastPay {
    /**
     * 协议支付
     * 返回状态有可能需要验证确认支付
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "fastPay/apply", method = RequestMethod.POST)
    Response<ChannelTransResponse> fastPayApply(@RequestBody @Valid Request<ChannelTransRequest> request);

    /**
     * 协议支付提交
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "fastPay/confirm", method = RequestMethod.POST)
    Response<ChannelTransResponse> fastPayConfirm(@RequestBody @Valid Request<ChannelTransConfirmRequest> request);

    /**
     * 交易查询
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "fastPay/query", method = RequestMethod.POST)
    Response<ChannelTransResponse> fastPayQuery(@RequestBody @Valid Request<ChannelTransQueryRequest> request);
}
