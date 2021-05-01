package com.noahpay.pay.risk.iface;


import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.risk.bean.req.MerchantUseRequest;
import com.noahpay.pay.risk.iface.fallback.RiskFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * 风控服务
 *
 * @author chenliang
 */
@FeignClient(name = "risk",
        fallbackFactory = RiskFallbackFactory.class
)
public interface IRiskDataHandle {
    /**
     * 更新商户日使用额
     *
     * @param request 请求参数
     * @return 处理结果
     */
    @RequestMapping(value = "risk/handle/updateMerchantUse", method = RequestMethod.POST)
    Response updateMerchantUse(@RequestBody @Valid Request<MerchantUseRequest> request);
}