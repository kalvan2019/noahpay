package com.noahpay.pay.cust.iface.fallback;

import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.cust.bean.req.*;
import com.noahpay.pay.cust.bean.res.*;
import com.noahpay.pay.cust.bean.req.*;
import com.noahpay.pay.cust.bean.res.*;
import com.noahpay.pay.cust.constant.CustReturnCode;
import com.noahpay.pay.cust.iface.*;
import com.noahpay.pay.cust.iface.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author chenliang
 */
public class CustFallback implements IPartner, IMerchant, ISubMerchant, ICust, IProtocol, INotify, ICustDataHandle {

    @Override
    public Response<CustRegisterResponse> custRegister(@Valid Request<CustRegisterRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<List<CustQueryResponse>> custQuery(@Valid Request<CustQueryRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response clearProtocolBill(@Valid Request request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<MerchantRegisterResponse> merchantRegister(@Valid Request<MerchantRegisterRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<MerchantQueryResponse> merchantQuery(@Valid Request<Long> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<MerchantCheckTransResponse> merchantCheckTrans(@Valid Request<MerchantCheckTransRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response notifyChannelProtocolSign(@Valid Request<NotifyChannelProtocolSignRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response notifyMerchantProtocolSign(@Valid Request<NotifyMerchantProtocolSignRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<PartnerRegisterResponse> partnerRegister(@Valid Request<PartnerRegisterRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response partnerQuery(@Valid Request<Long> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<ProtocolSignResponse> protocolSign(@Valid Request<ProtocolSignRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<ProtocolSignResponse> protocolSignConfirm(@Valid Request<ProtocolSignConfirmRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<ProtocolSignResponse> protocolQuery(@Valid Request<ProtocolQueryRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<SubMerchantRegisterResponse> subMerchantRegister(@Valid Request<SubMerchantRegisterRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response subMerchantModifyBankCard(@Valid Request<ModifyBankCardRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response subMerchantModifyFee(@Valid Request<ModifyFeeRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<List<SubMerchantQueryResponse>> subMerchantQuery(@Valid Request<Long> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }

    @Override
    public Response<List<SubMerchantCheckProtocolResponse>> subMerchantCheckProtocol(@Valid Request<SubMerchantCheckProtocolRequest> request) {
        return Response.buildResult(CustReturnCode.FALLBACK);
    }
}
