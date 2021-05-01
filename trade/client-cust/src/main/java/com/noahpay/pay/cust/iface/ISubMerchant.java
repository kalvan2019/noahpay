package com.noahpay.pay.cust.iface;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.cust.bean.req.ModifyBankCardRequest;
import com.noahpay.pay.cust.bean.req.ModifyFeeRequest;
import com.noahpay.pay.cust.bean.req.SubMerchantCheckProtocolRequest;
import com.noahpay.pay.cust.bean.req.SubMerchantRegisterRequest;
import com.noahpay.pay.cust.bean.res.SubMerchantCheckProtocolResponse;
import com.noahpay.pay.cust.bean.res.SubMerchantQueryResponse;
import com.noahpay.pay.cust.bean.res.SubMerchantRegisterResponse;
import com.noahpay.pay.cust.iface.fallback.CustFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * 子商户信息服务
 *
 * @author chenliang
 */
@FeignClient(name = "cust",
        fallbackFactory = CustFallbackFactory.class
)
public interface ISubMerchant {

    /**
     * 子商户注册
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "subMerchant/register", method = RequestMethod.POST)
    Response<SubMerchantRegisterResponse> subMerchantRegister(@Valid @RequestBody Request<SubMerchantRegisterRequest> request);

    /**
     * 子商户结算卡修改
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "subMerchant/modifyBankCard", method = RequestMethod.POST)
    Response subMerchantModifyBankCard(@Valid @RequestBody Request<ModifyBankCardRequest> request);

    /**
     * 子商户费率修改
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "subMerchant/modifyFee", method = RequestMethod.POST)
    Response subMerchantModifyFee(@Valid @RequestBody Request<ModifyFeeRequest> request);

    /**
     * 子商户信息查询
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "subMerchant/query", method = RequestMethod.POST)
    Response<List<SubMerchantQueryResponse>> subMerchantQuery(@Valid @RequestBody Request<Long> request);

    /**
     * 子商户协议检查
     *
     * @param request 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "subMerchant/checkProtocol", method = RequestMethod.POST)
    Response<List<SubMerchantCheckProtocolResponse>> subMerchantCheckProtocol(@Valid @RequestBody Request<SubMerchantCheckProtocolRequest> request);
}
