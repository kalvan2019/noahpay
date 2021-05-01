package com.noahpay.pay.channel.iface;

import com.noahpay.pay.channel.bean.req.ChannelBaseRequest;
import com.noahpay.pay.channel.bean.req.ChannelMerRegisterRequest;
import com.noahpay.pay.channel.bean.req.ChannelUploadImageRequest;
import com.noahpay.pay.channel.bean.res.ChannelMerRegisterResponse;
import com.noahpay.pay.channel.bean.res.ChannelQueryBalanceResponse;
import com.noahpay.pay.channel.bean.res.ChannelUploadImageResponse;
import com.noahpay.pay.channel.iface.fallback.ChannelFallbackFactory;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 商户信息服务
 *
 * @author chenliang
 */
@FeignClient(name = "channel",
        fallbackFactory = ChannelFallbackFactory.class
)
@RestController
public interface IChannelMerchant {
    /**
     * 进件图片上传
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "merchant/imageUpload", method = RequestMethod.POST)
    Response<ChannelUploadImageResponse> merchantImageUpload(@RequestBody @Valid Request<ChannelUploadImageRequest> request);

    /**
     * 商户进件
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "merchant/register", method = RequestMethod.POST)
    Response<ChannelMerRegisterResponse> merchantRegister(@RequestBody @Valid Request<ChannelMerRegisterRequest> request);

    /**
     * 查询通道商户余额
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "merchant/balance", method = RequestMethod.POST)
    Response<ChannelQueryBalanceResponse> merchantBalance(@RequestBody @Valid Request<ChannelBaseRequest> request);

    /**
     * 查询通道子商户余额
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "subMerchant/balance", method = RequestMethod.POST)
    Response<ChannelQueryBalanceResponse> subMerchantBalance(@RequestBody @Valid Request<ChannelBaseRequest> request);
}
