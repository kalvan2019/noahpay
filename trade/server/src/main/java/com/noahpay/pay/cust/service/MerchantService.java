package com.noahpay.pay.cust.service;

import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.MerchantInfoMapper;
import com.noahpay.pay.commons.db.cust.model.MerchantInfo;
import com.noahpay.pay.commons.db.cust.model.MerchantProductTrans;
import com.noahpay.pay.cust.bean.model.BankCardInfo;
import com.noahpay.pay.cust.bean.model.CompanyInfo;
import com.noahpay.pay.cust.bean.req.CustRegisterRequest;
import com.noahpay.pay.cust.bean.req.MerchantCheckTransRequest;
import com.noahpay.pay.cust.bean.req.MerchantRegisterRequest;
import com.noahpay.pay.cust.bean.res.CustRegisterResponse;
import com.noahpay.pay.cust.bean.res.MerchantCheckTransResponse;
import com.noahpay.pay.cust.bean.res.MerchantRegisterResponse;
import com.noahpay.pay.cust.constant.CustReturnCode;
import com.noahpay.pay.cust.constant.CustStateEnum;
import com.noahpay.pay.cust.constant.CustTypeEnum;
import com.kalvan.web.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Slf4j
@Service
public class MerchantService {
    @Resource
    MerchantInfoMapper merchantInfoMapper;
    @Resource
    CustUniqueCodeService custUniqueCodeService;
    @Resource
    CustService custService;
    @Resource
    CacheMerchantService cacheMerchantService;

    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public Response<MerchantRegisterResponse> register(Request<MerchantRegisterRequest> request) {
        MerchantRegisterRequest merchantRegisterRequest = request.getData();
        //开通客户支持幂等
        CustRegisterRequest custRegisterRequest = new CustRegisterRequest();
        BeanUtils.copyProperties(merchantRegisterRequest, custRegisterRequest);
        Response<CustRegisterResponse> custRegisterResponse = custService.register(new Request<>().setData(custRegisterRequest));
        if (!custRegisterResponse.success()) {
            return Response.buildResult(custRegisterResponse);
        }
        Long merchantCustNo = custRegisterResponse.getData().getCustNo();
        //开通商户
        CompanyInfo companyInfo = merchantRegisterRequest.getCompanyInfo();
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setMerchantNo(custUniqueCodeService.getUniqueNoDefault(CustTypeEnum.MERCHANT));
        merchantInfo.setMerchantName(merchantRegisterRequest.getMerchantName());
        merchantInfo.setBusinessLicenseNo(companyInfo.getBusinessLicenseNo());
        BankCardInfo bankCardInfo = merchantRegisterRequest.getBankCardInfo();
        BeanUtils.copyProperties(bankCardInfo, merchantInfo);
        merchantInfo.setState(CustStateEnum.INVALID.code);
        merchantInfoMapper.insertSelective(merchantInfo);
        //返回开户结果
        MerchantRegisterResponse merchantRegisterResponse = new MerchantRegisterResponse();
        merchantRegisterResponse.setMerchantNo(merchantInfo.getMerchantNo());
        merchantRegisterResponse.setState(merchantInfo.getState());
        return Response.buildSuccess().setData(merchantRegisterResponse);
    }

    public Response<MerchantCheckTransResponse> checkTrans(Request<MerchantCheckTransRequest> request) {
        MerchantCheckTransRequest merchantCheckTransRequest = request.getData();
        MerchantInfo merchantInfo = checkMerchant(merchantCheckTransRequest.getMerchantNo());
        MerchantProductTrans merchantProductTrans = cacheMerchantService.getMerchantProductTrans(merchantCheckTransRequest.getMerchantNo(), merchantCheckTransRequest.getTransType());
        if (merchantProductTrans == null) {
            return Response.buildResult(CustReturnCode.DATA_NOT_EXISTS.formatMessage("入网协议"));
        }
        if (CustStateEnum.VALID.code != merchantProductTrans.getState() || merchantProductTrans.getEffectiveDate() > DateUtil.getDateInteger()) {
            return Response.buildResult(CustReturnCode.FAIL.formatMessage("入网协议未生效"));
        }
        if (merchantProductTrans.getExpiryDate() < DateUtil.getDateInteger()) {
            return Response.buildResult(CustReturnCode.FAIL.formatMessage("入网协议已失效"));
        }
        if (merchantCheckTransRequest.getAmount() > merchantProductTrans.getLimitMaxAmount()) {
            throw new BizException(CustReturnCode.FAIL.formatMessage("订单金额大于协议上限"));
        }
        MerchantCheckTransResponse response = new MerchantCheckTransResponse();
        response.setMerchantName(merchantInfo.getMerchantName());
        BeanUtils.copyProperties(merchantProductTrans, response);
        return Response.buildSuccess().setData(response);
    }

    private MerchantInfo checkMerchant(Long merchantNo) {
        MerchantInfo merchantInfo = cacheMerchantService.getMerchant(merchantNo);
        if (merchantInfo == null) {
            throw new BizException(CustReturnCode.DATA_NOT_EXISTS.formatMessage("商户"));
        }
        if (CustStateEnum.VALID.code != merchantInfo.getState()) {
            throw new BizException(CustReturnCode.FAIL.formatMessage("商户未生效"));
        }
        return merchantInfo;
    }
}
