package com.noahpay.pay.trade.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.trade.bean.req.CallBackRequest;
import com.noahpay.pay.trade.bean.req.OrderNotifyRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.iface.fallback.TradeFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知接口
 *
 * @author chenliang
 */
@FeignClient(name = "trade",
        fallbackFactory = TradeFallbackFactory.class
)
//@RestController
public interface INotify {
    /**
     * 渠道回调
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "notify/callback", method = RequestMethod.POST)
    Response<TransResponse> callback(@Validated @RequestBody Request<CallBackRequest> request);

    /**
     * 通知商户
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "notify/merchant", method = RequestMethod.POST)
    Response notifyMerchant(@Validated @RequestBody Request<String> request);

    /**
     * 通知状态更新
     * 根据交易流水号处理通知状态
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "notify/updateNotifyState", method = RequestMethod.POST)
    Response updateNotifyState(@Validated @RequestBody Request<OrderNotifyRequest> request);
}