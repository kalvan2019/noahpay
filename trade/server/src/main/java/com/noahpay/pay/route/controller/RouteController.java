package com.noahpay.pay.route.controller;

import com.noahpay.pay.route.service.RouteService;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.route.bean.req.RouteDfRequest;
import com.noahpay.pay.route.bean.req.RouteOrderRequest;
import com.noahpay.pay.route.bean.req.RouteRequest;
import com.noahpay.pay.route.bean.res.RouteResponse;
import com.noahpay.pay.route.iface.IRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author chenliang
 */
@Slf4j
@RestController
public class RouteController implements IRoute {
    @Resource
    RouteService routeService;

    @Override
    public Response<RouteResponse> orderRoute(@Valid Request<RouteOrderRequest> request) {
        return routeService.orderRoute(request.getData());
    }

    @Override
    public Response<RouteResponse> signRoute(@Valid Request<RouteOrderRequest> request) {
        return null;
    }

    @Override
    public Response<RouteResponse> dfRoute(@Valid Request<RouteDfRequest> request) {
        return null;
    }

    @Override
    public Response<RouteResponse> authRoute(@Valid Request<RouteRequest> request) {
        return null;
    }
}