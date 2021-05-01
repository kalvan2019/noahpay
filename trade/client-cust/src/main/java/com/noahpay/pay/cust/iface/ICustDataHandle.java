package com.noahpay.pay.cust.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.cust.iface.fallback.CustFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * 定时服务
 *
 * @author chenliang
 */
@FeignClient(name = "cust",
        fallbackFactory = CustFallbackFactory.class
)
public interface ICustDataHandle {

    /**
     * 清理签约流水表数据
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "handle/clearProtocolBill", method = RequestMethod.POST)
    Response clearProtocolBill(@Valid @RequestBody Request request);
}
