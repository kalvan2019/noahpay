package com.noahpay.pay.cust.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.cust.bean.req.PartnerRegisterRequest;
import com.noahpay.pay.cust.bean.res.PartnerRegisterResponse;
import com.noahpay.pay.cust.iface.fallback.CustFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * 合作方信息服务
 *
 * @author chenliang
 */
@FeignClient(name = "cust",
        fallbackFactory = CustFallbackFactory.class
)
public interface IPartner {

    /**
     * 合作方注册
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "partner/register", method = RequestMethod.POST)
    Response<PartnerRegisterResponse> partnerRegister(@Valid @RequestBody Request<PartnerRegisterRequest> request);

    /**
     * 合作方信息查询
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "partner/query", method = RequestMethod.POST)
    Response partnerQuery(@Valid @RequestBody Request<Long> request);
}
