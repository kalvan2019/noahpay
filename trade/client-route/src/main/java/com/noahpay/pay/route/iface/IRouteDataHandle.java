package com.noahpay.pay.route.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.route.bean.req.ChannelUseRequest;
import com.noahpay.pay.route.iface.fallback.RouteFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * 路由数据维护服务
 *
 * @author chenliang
 */
@FeignClient(name = "base",
        fallbackFactory = RouteFallbackFactory.class
)
public interface IRouteDataHandle {

    /**
     * 更新通道使用额
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "route/handle/updateChannelUse", method = RequestMethod.POST)
    Response updateChannelUse(@RequestBody @Valid Request<ChannelUseRequest> request);

    /**
     * 重置通道使用额
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "route/handle/resetChannelUse", method = RequestMethod.POST)
    Response resetChannelUse(@RequestBody @Valid Request request);

    /**
     * 更新通道垫资日使用额
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "route/handle/updateAdvanceUseAmount", method = RequestMethod.POST)
    Response updateAdvanceUseAmount(@RequestBody @Valid Request request);

    /**
     * 重置通道垫资日使用额
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "route/handle/resetAdvanceUseAmount", method = RequestMethod.POST)
    Response resetAdvanceUseAmount(@RequestBody @Valid Request request);
}
