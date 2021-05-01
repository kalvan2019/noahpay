package com.noahpay.pay.channel.iface;

import com.noahpay.pay.channel.bean.req.ChannelRefundQueryRequest;
import com.noahpay.pay.channel.bean.req.ChannelRefundRequest;
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
 * 退款接口
 *
 * @author chenliang
 */
@FeignClient(name = "channel",
        fallbackFactory = ChannelFallbackFactory.class
)
@RestController
public interface IChannelRefund {
    /**
     * 退款
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "refund/trans", method = RequestMethod.POST)
    Response<ChannelTransResponse> refundTrans(@RequestBody @Valid Request<ChannelRefundRequest> request);

    /**
     * 退款查询
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "refund/query", method = RequestMethod.POST)
    Response<ChannelTransResponse> refundQuery(@RequestBody @Valid Request<ChannelRefundQueryRequest> request);
}
