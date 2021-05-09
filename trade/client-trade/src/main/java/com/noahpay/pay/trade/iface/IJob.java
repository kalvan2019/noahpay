package com.noahpay.pay.trade.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.trade.bean.req.OrderManualRequest;
import com.noahpay.pay.trade.bean.res.TransResponse;
import com.noahpay.pay.trade.iface.fallback.TradeFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务接口
 *
 * @author chenliang
 */
@FeignClient(name = "trade",
        fallbackFactory = TradeFallbackFactory.class
)
//@RestController
public interface IJob {
    /**
     * 同步渠道状态
     *
     * @param request 交易流水号
     * @return 查询结果
     */
    @RequestMapping(value = "job/synQuery", method = RequestMethod.POST)
    Response<TransResponse> synQuery(@Validated @RequestBody Request<String> request);

    /**
     * 交易状态人工重置
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "job/manualHandling", method = RequestMethod.POST)
    Response manualHandling(@Validated @RequestBody Request<OrderManualRequest> request);
}
