package com.noahpay.pay.trade.process.route;

import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.commons.db.trade.model.PayBill;
import com.noahpay.pay.route.controller.RouteController;
import com.noahpay.pay.trade.process.template.BaseRoute;
import com.noahpay.pay.route.bean.req.RouteOrderRequest;
import com.noahpay.pay.route.bean.res.RouteResponse;
import com.noahpay.pay.route.iface.IRoute;
import com.noahpay.pay.trade.constant.TransReturnCode;
import com.noahpay.pay.trade.service.PayBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenliang
 */
@Component
@Slf4j
public class PayBillRoute extends BaseRoute<PayBill> {
    /**
     * TODO 切換为微服务 IRoute
     */
    @Resource
    RouteController iRoute;
    @Resource
    PayBillService payBillService;

    @Override
    public void route(PayBill bill) {
        log.debug("交易路由");
        RouteOrderRequest routeOrderRequest = new RouteOrderRequest();
        routeOrderRequest.setPayType(bill.getPayType());
        routeOrderRequest.setMerchantNo(bill.getMerchantNo());
        routeOrderRequest.setChannelMerchantNo(bill.getChannelMerchantNo());
        routeOrderRequest.setOrderAmount(bill.getAmount());
        Response<RouteResponse> response = iRoute.orderRoute(new Request<>().setData(routeOrderRequest));
        if (!response.success()) {
            throw new BizException(response);
        }
        RouteResponse routeResponse = response.getData();
        bill.setChannelNo(routeResponse.getChannelNo());
        bill.setChannelMerchantNo(routeResponse.getChannelMerchantNo());
        bill.setChannelSubMerchantNo(routeResponse.getChannelSubMerchantNo());
        int updateRouterRow = payBillService.updateRoute(bill);
        if (updateRouterRow != 1) {
            throw new BizException(TransReturnCode.DATA_STATE_ERROR.formatMessage(bill.getOrderId()));
        }
    }
}
