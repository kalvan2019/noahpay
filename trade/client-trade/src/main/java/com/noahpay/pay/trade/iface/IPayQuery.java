package com.noahpay.pay.trade.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.trade.bean.req.OrderQueryRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.iface.fallback.TradeFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查询接口
 *
 * @author chenliang
 */
@FeignClient(name = "trade",
        fallbackFactory = TradeFallbackFactory.class
)
//@RestController
public interface IPayQuery {
    /**
     * 查询订单
     *
     * @param request 请求参数
     * @return 返回订单支付结果
     */
    @RequestMapping(value = "pay/query", method = RequestMethod.POST)
    Response<TransResponse> query(@Validated @RequestBody Request<OrderQueryRequest> request);
}