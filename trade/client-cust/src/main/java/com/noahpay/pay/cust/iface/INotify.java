package com.noahpay.pay.cust.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.cust.bean.req.NotifyChannelProtocolSignRequest;
import com.noahpay.pay.cust.bean.req.NotifyMerchantProtocolSignRequest;
import com.noahpay.pay.cust.iface.fallback.CustFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * 通知回调服务
 *
 * @author chenliang
 */
@FeignClient(name = "cust",
        fallbackFactory = CustFallbackFactory.class
)
public interface INotify {
    /**
     * 签约通道异步回调
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "notify/channelProtocolSign", method = RequestMethod.POST)
    Response notifyChannelProtocolSign(@Valid @RequestBody Request<NotifyChannelProtocolSignRequest> request);

    /**
     * 签约商户异步回调
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "notify/merchantProtocolSign", method = RequestMethod.POST)
    Response notifyMerchantProtocolSign(@Valid @RequestBody Request<NotifyMerchantProtocolSignRequest> request);
}
