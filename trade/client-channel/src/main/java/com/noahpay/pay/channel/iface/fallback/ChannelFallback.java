package com.noahpay.pay.channel.iface.fallback;

import com.noahpay.pay.channel.bean.model.CheckDetailInfo;
import com.noahpay.pay.channel.bean.req.*;
import com.noahpay.pay.channel.bean.res.*;
import com.noahpay.pay.channel.constant.ChannelReturnCode;
import com.noahpay.pay.channel.iface.*;
import com.kalvan.client.constant.CommonStateEnum;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;

import javax.validation.Valid;
import java.util.List;

/**
 * @author chenliang
 */
public class ChannelFallback implements IChannelAuth,
        IChannelBase,
        IChannelCheck,
        IChannelDf,
        IChannelDs,
        IChannelGateway,
        IChannelMerchant,
        IChannelQrPay,
        IChannelFastPay,
        IChannelRefund,
        IChannelSign {
    @Override
    public Response<ChannelTransResponse> authTrans(@Valid Request<ChannelTransRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> authQuery(@Valid Request<ChannelTransQueryRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelConvertResponse> channelParamConvert(@Valid Request<ChannelConvertRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelCallbackResponse> callback(@Valid Request<ChannelCallbackRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response asyncCheck(@Valid Request<ChannelCheckRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<List<CheckDetailInfo>> syncCheck(@Valid Request<ChannelCheckRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> dfTrans(@Valid Request<ChannelTransRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> dfQuery(@Valid Request<ChannelTransQueryRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> dsTrans(@Valid Request<ChannelTransRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> dsQuery(@Valid Request<ChannelTransQueryRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> gatewayTrans(@Valid Request<ChannelTransRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> gatewayQuery(@Valid Request<ChannelTransQueryRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelUploadImageResponse> merchantImageUpload(@Valid Request<ChannelUploadImageRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelMerRegisterResponse> merchantRegister(@Valid Request<ChannelMerRegisterRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelQueryBalanceResponse> merchantBalance(@Valid Request<ChannelBaseRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelQueryBalanceResponse> subMerchantBalance(@Valid Request<ChannelBaseRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> qrPayB2c(@Valid Request<ChannelTransRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> qrPayC2b(@Valid Request<ChannelTransRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> qrPayQuery(@Valid Request<ChannelTransQueryRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> fastPayApply(@Valid Request<ChannelTransRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> fastPayConfirm(@Valid Request<ChannelTransConfirmRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> fastPayQuery(@Valid Request<ChannelTransQueryRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> refundTrans(@Valid Request<ChannelRefundRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelTransResponse> refundQuery(@Valid Request<ChannelRefundQueryRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelSignProtocolResponse> signApply(@Valid Request<ChannelSignProtocolRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelSignProtocolResponse> signConfirm(@Valid Request<ChannelSignProtocolConfirmRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }

    @Override
    public Response<ChannelSignProtocolResponse> signQuery(@Valid Request<ChannelSignQueryRequest> request) {
        return Response.buildResult(ChannelReturnCode.FALLBACK).setState(CommonStateEnum.OVERTIME.code);
    }
}
