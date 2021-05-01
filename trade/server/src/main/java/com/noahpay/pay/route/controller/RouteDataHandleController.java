package com.noahpay.pay.route.controller;

import com.noahpay.pay.route.service.ChannelService;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.route.bean.req.ChannelUseRequest;
import com.noahpay.pay.route.iface.IRouteDataHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author chenliang
 */
@Slf4j
@RestController
public class RouteDataHandleController implements IRouteDataHandle {
    @Resource
    private ChannelService channelService;

    @Override
    public Response updateChannelUse(@Valid Request<ChannelUseRequest> request) {
        return channelService.updateChannelUse(request.getData());
    }

    @Override
    public Response resetChannelUse(@Valid Request request) {
        return channelService.resetChannelUse();
    }

    @Override
    public Response resetAdvanceUseAmount(@Valid Request request) {
        return null;
    }

    @Override
    public Response updateAdvanceUseAmount(@Valid Request request) {
        return null;
    }
}