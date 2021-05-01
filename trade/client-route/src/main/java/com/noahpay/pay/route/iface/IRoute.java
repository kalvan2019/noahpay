package com.noahpay.pay.route.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.route.bean.req.RouteDfRequest;
import com.noahpay.pay.route.bean.req.RouteOrderRequest;
import com.noahpay.pay.route.bean.req.RouteRequest;
import com.noahpay.pay.route.bean.res.RouteResponse;
import com.noahpay.pay.route.iface.fallback.RouteFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * 路由服务
 *
 * @author chenliang
 */
@FeignClient(name = "base",
        fallbackFactory = RouteFallbackFactory.class
)
public interface IRoute {

    /**
     * 收款路由
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "route/order", method = RequestMethod.POST)
    Response<RouteResponse> orderRoute(@RequestBody @Valid Request<RouteOrderRequest> request);

    /**
     * 签约路由
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "route/sign", method = RequestMethod.POST)
    Response<RouteResponse> signRoute(@RequestBody @Valid Request<RouteOrderRequest> request);

    /**
     * 付款路由
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "route/df", method = RequestMethod.POST)
    Response<RouteResponse> dfRoute(@RequestBody @Valid Request<RouteDfRequest> request);

    /**
     * 鉴权路由
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "route/auth", method = RequestMethod.POST)
    Response<RouteResponse> authRoute(@RequestBody @Valid Request<RouteRequest> request);
}
