package com.noahpay.pay.trade.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.trade.bean.req.TransRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.iface.fallback.TradeFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 支付接口
 *
 * @author chenliang
 */
@FeignClient(name = "trade",
        fallbackFactory = TradeFallbackFactory.class
)
public interface ITrans {
    /**
     * 统一下单接口
     *
     * @param request 请求参数
     * @return 返回交易接口
     */
    @RequestMapping(value = "pay/order", method = RequestMethod.POST)
    Response<TransResponse> order(@Validated @RequestBody Request<TransRequest> request);

}