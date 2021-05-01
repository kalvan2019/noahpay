package com.noahpay.pay.channel.iface;

import com.noahpay.pay.channel.bean.req.ChannelSignProtocolConfirmRequest;
import com.noahpay.pay.channel.bean.req.ChannelSignProtocolRequest;
import com.noahpay.pay.channel.bean.req.ChannelSignQueryRequest;
import com.noahpay.pay.channel.bean.res.ChannelSignProtocolResponse;
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
 * 协议签约
 *
 * @author chenliang
 */
@FeignClient(name = "channel",
        fallbackFactory = ChannelFallbackFactory.class
)
@RestController
public interface IChannelSign {
    /**
     * 协议支付签约申请
     * 返回状态有可能需要验证则走签约验证接口
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "sign/apply", method = RequestMethod.POST)
    Response<ChannelSignProtocolResponse> signApply(@RequestBody @Valid Request<ChannelSignProtocolRequest> request);

    /**
     * 协议支付签约确认
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "sign/confirm", method = RequestMethod.POST)
    Response<ChannelSignProtocolResponse> signConfirm(@RequestBody @Valid Request<ChannelSignProtocolConfirmRequest> request);

    /**
     * 签约查询
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "sign/query", method = RequestMethod.POST)
    Response<ChannelSignProtocolResponse> signQuery(@RequestBody @Valid Request<ChannelSignQueryRequest> request);
}
