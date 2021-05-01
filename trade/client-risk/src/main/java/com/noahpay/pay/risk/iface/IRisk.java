package com.noahpay.pay.risk.iface;


import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.risk.bean.req.RiskBlackRequest;
import com.noahpay.pay.risk.bean.req.RiskTransRequest;
import com.noahpay.pay.risk.bean.res.RiskResponse;
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
public interface IRisk {
    /**
     * 黑名单检查
     *
     * @param request 请求参数
     * @return 处理结果
     */
    @RequestMapping(value = "risk/checkBlack", method = RequestMethod.POST)
    Response<RiskResponse> checkBlack(@RequestBody @Valid Request<RiskBlackRequest> request);

    /**
     * 交易风控检查
     *
     * @param request 请求参数
     * @return 处理结果
     */
    @RequestMapping(value = "risk/checkTrans", method = RequestMethod.POST)
    Response<RiskResponse> checkTrans(@RequestBody @Valid Request<RiskTransRequest> request);
}