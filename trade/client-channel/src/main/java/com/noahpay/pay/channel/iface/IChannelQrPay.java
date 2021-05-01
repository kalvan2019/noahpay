package com.noahpay.pay.channel.iface;

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
 * 扫码支付
 *
 * @author chenliang
 */
@FeignClient(name = "channel",
        fallbackFactory = ChannelFallbackFactory.class
)
@RestController
public interface IChannelQrPay {
    /**
     * 收款码主扫支付
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "qrPay/b2c", method = RequestMethod.POST)
    Response<ChannelTransResponse> qrPayB2c(@RequestBody @Valid Request<ChannelTransRequest> request);

    /**
     * 付款码被扫支付
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "qrPay/c2b", method = RequestMethod.POST)
    Response<ChannelTransResponse> qrPayC2b(@RequestBody @Valid Request<ChannelTransRequest> request);

    /**
     * 二维码订单查收
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "qrPay/query", method = RequestMethod.POST)
    Response<ChannelTransResponse> qrPayQuery(@RequestBody @Valid Request<ChannelTransQueryRequest> request);
}
