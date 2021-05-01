package com.noahpay.pay.channel.iface;

import com.noahpay.pay.channel.bean.req.ChannelTransQueryRequest;
import com.noahpay.pay.channel.bean.req.ChannelTransRequest;
import com.noahpay.pay.channel.bean.res.ChannelTransResponse;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.channel.iface.fallback.ChannelFallbackFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 代付交易
 *
 * @author chenliang
 */
@org.springframework.cloud.openfeign.FeignClient(name = "channel",
        fallbackFactory = ChannelFallbackFactory.class
)
@RestController
public interface IChannelDf {
    /**
     * 代付接口
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "df/trans", method = RequestMethod.POST)
    Response<ChannelTransResponse> dfTrans(@RequestBody @Valid Request<ChannelTransRequest> request);

    /**
     * 交易查询
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "df/query", method = RequestMethod.POST)
    Response<ChannelTransResponse> dfQuery(@RequestBody @Valid Request<ChannelTransQueryRequest> request);
}
