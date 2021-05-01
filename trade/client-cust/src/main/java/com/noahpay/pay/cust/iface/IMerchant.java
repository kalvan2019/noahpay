package com.noahpay.pay.cust.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.cust.bean.req.MerchantCheckTransRequest;
import com.noahpay.pay.cust.bean.req.MerchantRegisterRequest;
import com.noahpay.pay.cust.bean.res.MerchantCheckTransResponse;
import com.noahpay.pay.cust.bean.res.MerchantQueryResponse;
import com.noahpay.pay.cust.bean.res.MerchantRegisterResponse;
import com.noahpay.pay.cust.iface.fallback.CustFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * 商户信息服务
 *
 * @author chenliang
 */
@FeignClient(name = "cust",
        fallbackFactory = CustFallbackFactory.class
)
public interface IMerchant {

    /**
     * 商户注册
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "cust/merchant/register", method = RequestMethod.POST)
    Response<MerchantRegisterResponse> merchantRegister(@Valid @RequestBody Request<MerchantRegisterRequest> request);

    /**
     * 商户信息查询
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "cust/merchant/query", method = RequestMethod.POST)
    Response<MerchantQueryResponse> merchantQuery(@Valid @RequestBody Request<Long> request);

    /**
     * 商户业务入网检查
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "cust/merchant/checkTrans", method = RequestMethod.POST)
    Response<MerchantCheckTransResponse> merchantCheckTrans(@Valid @RequestBody Request<MerchantCheckTransRequest> request);
}
