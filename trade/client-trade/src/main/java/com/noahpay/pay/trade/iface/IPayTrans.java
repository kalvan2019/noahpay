package com.noahpay.pay.trade.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.trade.bean.req.MicroPayRequest;
import com.noahpay.pay.trade.bean.req.OrderRequest;
import com.noahpay.pay.trade.bean.req.UnifiedOrderRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.iface.fallback.TradeFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易接口
 *
 * @author chenliang
 */
@FeignClient(name = "trade",
        fallbackFactory = TradeFallbackFactory.class
)
//@RestController
public interface IPayTrans {
    /**
     * 支付下单
     *
     * @param request 请求参数
     * @return 返回下单结果
     */
    @RequestMapping(value = "pay/unifiedorder", method = RequestMethod.POST)
    Response<TransResponse> unifiedOrder(@Validated @RequestBody Request<UnifiedOrderRequest> request);

    /**
     * 付款码支付
     *
     * @param request 请求参数
     * @return 返回支付结果
     */
    @RequestMapping(value = "pay/micropay", method = RequestMethod.POST)
    Response<TransResponse> microPay(@Validated @RequestBody Request<MicroPayRequest> request);
}