package com.noahpay.pay.cust.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.cust.bean.req.CustQueryRequest;
import com.noahpay.pay.cust.bean.req.CustRegisterRequest;
import com.noahpay.pay.cust.bean.res.CustQueryResponse;
import com.noahpay.pay.cust.bean.res.CustRegisterResponse;
import com.noahpay.pay.cust.iface.fallback.CustFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * 客户信息服务
 *
 * @author chenliang
 */
@FeignClient(name = "cust",
        fallbackFactory = CustFallbackFactory.class
)
public interface ICust {

    /**
     * 客户注册
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "cust/register", method = RequestMethod.POST)
    Response<CustRegisterResponse> custRegister(@Valid @RequestBody Request<CustRegisterRequest> request);

    /**
     * 客户信息查询
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "cust/query", method = RequestMethod.POST)
    Response<List<CustQueryResponse>> custQuery(@Valid @RequestBody Request<CustQueryRequest> request);
}
