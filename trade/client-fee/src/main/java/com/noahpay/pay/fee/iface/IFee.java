package com.noahpay.pay.fee.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.fee.bean.req.FeeCalculateRequest;
import com.noahpay.pay.fee.bean.res.FeeCalculateResponse;
import com.noahpay.pay.fee.iface.fallback.FeeFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * 计费服务
 *
 * @author chenliang
 */
@FeignClient(name = "cust",
        fallbackFactory = FeeFallbackFactory.class
)
public interface IFee {

    /**
     * 计费
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "fee/calculate", method = RequestMethod.POST)
    Response<FeeCalculateResponse> feeCalculate(@Valid @RequestBody Request<FeeCalculateRequest> request);
}
