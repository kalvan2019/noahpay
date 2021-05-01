package com.noahpay.pay.cust.service;

import com.kalvan.client.exception.BizException;
import com.kalvan.client.model.Request;
import com.kalvan.client.model.Response;
import com.noahpay.pay.commons.db.DataSourceConstants;
import com.noahpay.pay.commons.db.cust.mapper.CustInfoMapper;
import com.noahpay.pay.commons.db.cust.model.CustInfo;
import com.noahpay.pay.cust.bean.model.CertificateInfo;
import com.noahpay.pay.cust.bean.req.CustRegisterRequest;
import com.noahpay.pay.cust.bean.res.CustRegisterResponse;
import com.noahpay.pay.cust.constant.CustReturnCode;
import com.noahpay.pay.cust.constant.CustStateEnum;
import lombok.extern.slf4j.Slf4j;
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
public class CustService {
    @Resource
    CustInfoMapper custInfoMapper;
    @Resource
    CustUniqueCodeService custUniqueCodeService;

    @Transactional(value = DataSourceConstants.CUST_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    public Response<CustRegisterResponse> register(Request<CustRegisterRequest> request) {
        CustRegisterRequest custRegisterRequest = request.getData();
        //开通客户
        CertificateInfo certificateInfo = custRegisterRequest.getCertificateInfo();
        //TODO 加密使用EncryptUtils.encrypt(certificateInfo.getCertificateNo());
        String certificateNo = certificateInfo.getCertificateNo();
        //分表跟身份证走通过身份证号查重
        String partTable = custUniqueCodeService.getCustPartTable(certificateNo);
        CustInfo custInfo = custInfoMapper.getCustInfoByUk(certificateNo, certificateInfo.getCertificateType(), partTable);
        if (custInfo != null) {
            if (!custInfo.getCertificateName().equals(certificateInfo.getCertificateName())) {
                throw new BizException(CustReturnCode.FAIL.formatMessage("证件信息有误"));
            }
        } else {
            custInfo = new CustInfo();
            custInfo.setCustNo(custUniqueCodeService.getCustNo(certificateNo));
            custInfo.setMobile(custRegisterRequest.getMobile());
            custInfo.setEmail(custRegisterRequest.getEmail());
            custInfo.setAddress(custRegisterRequest.getAddress());
            custInfo.setCertificateType(certificateInfo.getCertificateType());
            custInfo.setCertificateNo(certificateInfo.getCertificateNo());
            custInfo.setCertificateName(certificateInfo.getCertificateName());
            custInfo.setCertificateExpiry(certificateInfo.getCertificateExpiry());
            custInfo.setCertificateAddress(certificateInfo.getCertificateAddress());
            custInfo.setState(CustStateEnum.INVALID.code);
            custInfoMapper.insertSelective(custInfo);
        }
        //返回结果
        CustRegisterResponse custRegisterResponse = new CustRegisterResponse();
        custRegisterResponse.setCustNo(custInfo.getCustNo());
        custRegisterResponse.setState(custInfo.getState());
        return Response.buildSuccess().setData(custRegisterResponse);
    }
}
