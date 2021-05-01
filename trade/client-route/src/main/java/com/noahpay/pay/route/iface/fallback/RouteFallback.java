package com.noahpay.pay.route.iface.fallback;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.route.bean.req.ChannelUseRequest;
import com.noahpay.pay.route.bean.req.RouteDfRequest;
import com.noahpay.pay.route.bean.req.RouteOrderRequest;
import com.noahpay.pay.route.bean.req.RouteRequest;
import com.noahpay.pay.route.bean.res.RouteResponse;
import com.noahpay.pay.route.constant.RouteReturnCode;
import com.noahpay.pay.route.iface.IRoute;
import com.noahpay.pay.route.iface.IRouteDataHandle;

import javax.validation.Valid;

/**
 * @author chenliang
 */
public class RouteFallback implements IRoute, IRouteDataHandle {
    @Override
    public Response<RouteResponse> orderRoute(@Valid Request<RouteOrderRequest> request) {
        return Response.buildResult(RouteReturnCode.FALLBACK);
    }

    @Override
    public Response<RouteResponse> signRoute(@Valid Request<RouteOrderRequest> request) {
        return Response.buildResult(RouteReturnCode.FALLBACK);
    }

    @Override
    public Response<RouteResponse> dfRoute(@Valid Request<RouteDfRequest> request) {
        return Response.buildResult(RouteReturnCode.FALLBACK);
    }

    @Override
    public Response<RouteResponse> authRoute(@Valid Request<RouteRequest> request) {
        return Response.buildResult(RouteReturnCode.FALLBACK);
    }

    @Override
    public Response updateChannelUse(@Valid Request<ChannelUseRequest> request) {
        return Response.buildResult(RouteReturnCode.FALLBACK);
    }

    @Override
    public Response resetChannelUse(@Valid Request request) {
        return Response.buildResult(RouteReturnCode.FALLBACK);
    }

    @Override
    public Response resetAdvanceUseAmount(@Valid Request request) {
        return Response.buildResult(RouteReturnCode.FALLBACK);
    }

    @Override
    public Response updateAdvanceUseAmount(@Valid Request request) {
        return Response.buildResult(RouteReturnCode.FALLBACK);
    }
}
