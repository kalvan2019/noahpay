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
 * 网关支付
 *
 * @author chenliang
 */
@FeignClient(name = "channel",
        fallbackFactory = ChannelFallbackFactory.class
)
@RestController
public interface IChannelGateway {
    /**
     * 网关支付
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "gateway/trans", method = RequestMethod.POST)
    Response<ChannelTransResponse> gatewayTrans(@RequestBody @Valid Request<ChannelTransRequest> request);

    /**
     * 交易查询
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "gateway/query", method = RequestMethod.POST)
    Response<ChannelTransResponse> gatewayQuery(@RequestBody @Valid Request<ChannelTransQueryRequest> request);
}
