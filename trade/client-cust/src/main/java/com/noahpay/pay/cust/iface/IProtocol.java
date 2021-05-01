package com.noahpay.pay.cust.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.cust.bean.req.ProtocolQueryRequest;
import com.noahpay.pay.cust.bean.req.ProtocolSignConfirmRequest;
import com.noahpay.pay.cust.bean.req.ProtocolSignRequest;
import com.noahpay.pay.cust.bean.res.ProtocolSignResponse;
import com.noahpay.pay.cust.iface.fallback.CustFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * 客户协议服务
 *
 * @author chenliang
 */
@FeignClient(name = "cust",
        fallbackFactory = CustFallbackFactory.class
)
public interface IProtocol {
    /**
     * 绑卡签约
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "protocol/sign", method = RequestMethod.POST)
    Response<ProtocolSignResponse> protocolSign(@Valid @RequestBody Request<ProtocolSignRequest> request);

    /**
     * 绑卡签约确认
     * 需要二次验证再提交签约信息
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "protocol/signConfirm", method = RequestMethod.POST)
    Response<ProtocolSignResponse> protocolSignConfirm(@Valid @RequestBody Request<ProtocolSignConfirmRequest> request);

    /**
     * 绑卡签约查询
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "protocol/query", method = RequestMethod.POST)
    Response<ProtocolSignResponse> protocolQuery(@Valid @RequestBody Request<ProtocolQueryRequest> request);
}
