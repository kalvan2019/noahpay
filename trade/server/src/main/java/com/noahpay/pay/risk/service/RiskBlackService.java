package com.noahpay.pay.risk.service;

import com.alibaba.fastjson.JSONObject;
import com.kalvan.client.model.Response;
import com.noahpay.pay.commons.db.risk.mapper.RiskListMapper;
import com.noahpay.pay.commons.db.risk.model.BlackList;
import com.noahpay.pay.commons.db.risk.model.RiskList;
import com.noahpay.pay.risk.bean.req.RiskBlackRequest;
import com.noahpay.pay.risk.bean.res.RiskResponse;
import com.noahpay.pay.risk.constant.RiskLevelEnum;
import com.noahpay.pay.risk.constant.RiskReturnCode;
import com.noahpay.pay.risk.constant.RiskTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务Service实现类
 *
 * @author chenliang
 */
@Service
@Slf4j
public class RiskBlackService {

    @Resource
    RiskListMapper riskListMapper;
    @Resource
    CacheRiskService cacheRiskService;

    public Response<RiskResponse> checkBlack(RiskBlackRequest riskBlackRequest) {
        List<BlackList> blackLists = new ArrayList<>();
        RiskResponse riskResponse = new RiskResponse();
        riskResponse.setRiskLevel(RiskLevelEnum.RISK_LEVEL_ONE.code);
        try {
            List<BlackList> list = cacheRiskService.getBlackList();
            for (BlackList blackList : list) {
                if (riskBlackRequest.getCustNo() != null && riskBlackRequest.getCustNo() != 0 && riskBlackRequest.getCustNo().equals(blackList.getCustNo())) {
                    blackLists.add(blackList);
                    continue;
                }
                if (StringUtils.isNotBlank(riskBlackRequest.getCertificateNo()) && blackList.getCertificateNo().equals(riskBlackRequest.getCertificateNo())) {
                    blackLists.add(blackList);
                    continue;
                }
                if (riskBlackRequest.getBankAccountNo() != null && blackList.getBankAccountNo().equals(riskBlackRequest.getBankAccountNo())) {
                    blackLists.add(blackList);
                    continue;
                }
                if (StringUtils.isNotBlank(riskBlackRequest.getEmail()) && blackList.getEmail().equals(riskBlackRequest.getEmail())) {
                    blackLists.add(blackList);
                    continue;
                }
                if (StringUtils.isNotBlank(riskBlackRequest.getMobile()) && blackList.getMobile().equals(riskBlackRequest.getMobile())) {
                    blackLists.add(blackList);
                    continue;
                }
                if (StringUtils.isNotBlank(riskBlackRequest.getBusinessLicenseNo()) && blackList.getBusinessLicenseNo().equals(riskBlackRequest.getBusinessLicenseNo())) {
                    blackLists.add(blackList);
                }
            }
        } catch (Exception e) {
            log.error("检查黑名单异常", e);
            return Response.buildSuccess().setData(riskResponse);
        }
        if (blackLists.size() > 0) {
            JSONObject jsonParams = new JSONObject();
            JSONObject jsonParamsReason = new JSONObject();
            int riskLevel = RiskTypeEnum.BLACK_TYPE_GREY.code;
            for (BlackList blackList : blackLists) {
                if (RiskTypeEnum.BLACK_TYPE_BLACK.code == blackList.getBlackType()) {
                    riskLevel = RiskTypeEnum.BLACK_TYPE_BLACK.code;
                }
                if (riskBlackRequest.getMobile() != null && riskBlackRequest.getMobile().equals(blackList.getMobile())) {
                    log.error("手机号:" + riskBlackRequest.getMobile() + "命中风控");
                    jsonParams.put("mobile", riskBlackRequest.getMobile());
                    jsonParamsReason.put("mobile", RiskReturnCode.CODE_5406.getMessage());
                }
                if (riskBlackRequest.getBankAccountNo() != null && riskBlackRequest.getBankAccountNo().equals(blackList.getBankAccountNo())) {
                    log.error("银行卡号:" + riskBlackRequest.getBankAccountNo() + "命中风控");
                    jsonParams.put("bankAccountNo", riskBlackRequest.getBankAccountNo());
                    jsonParamsReason.put("bankAccountNo", RiskReturnCode.CODE_5404.getMessage());
                }
                if (riskBlackRequest.getCertificateNo() != null && riskBlackRequest.getCertificateNo().equals(blackList.getCertificateNo())) {
                    log.error("身份证号:" + riskBlackRequest.getCertificateNo() + "命中风控");
                    jsonParams.put("certificateNo", riskBlackRequest.getCertificateNo());
                    jsonParamsReason.put("certificateNo", RiskReturnCode.CODE_5403.getMessage());
                }
                if (riskBlackRequest.getEmail() != null && riskBlackRequest.getEmail().equals(blackList.getEmail())) {
                    log.error("邮箱:" + riskBlackRequest.getEmail() + "命中风控");
                    jsonParams.put("email", riskBlackRequest.getEmail());
                    jsonParamsReason.put("email", RiskReturnCode.CODE_5405.getMessage());
                }
                if (riskBlackRequest.getCustNo() != null && riskBlackRequest.getCustNo().equals(blackList.getCustNo())) {
                    log.error("客户号:" + riskBlackRequest.getCustNo() + "命中风控");
                    jsonParams.put("memberNo", riskBlackRequest.getCustNo());
                    jsonParamsReason.put("memberNo", RiskReturnCode.CODE_5402.getMessage());
                }
                if (riskBlackRequest.getBusinessLicenseNo() != null && riskBlackRequest.getBusinessLicenseNo().equals(blackList.getBusinessLicenseNo())) {
                    log.error("营业执照注册号:" + riskBlackRequest.getBusinessLicenseNo() + "命中风控");
                    jsonParams.put("businessLicenseNo", riskBlackRequest.getBusinessLicenseNo());
                    jsonParamsReason.put("businessLicenseNo", RiskReturnCode.CODE_5407.getMessage());
                }
            }
            RiskList riskList = new RiskList();
            if (riskLevel == RiskTypeEnum.BLACK_TYPE_GREY.code) {
                riskList.setRiskLevel(RiskLevelEnum.RISK_LEVEL_TWO.code);
            } else if (riskLevel == RiskTypeEnum.BLACK_TYPE_BLACK.code) {
                riskList.setRiskLevel(RiskLevelEnum.RISK_LEVEL_ZERO.code);
            }
            riskList.setRefTransId(riskBlackRequest.getRefTransId());
            riskList.setRiskParams(jsonParams.toJSONString());
            riskList.setRiskReason(jsonParamsReason.toJSONString());
            riskList.setCreateTime(new Date());
            riskListMapper.insertSelective(riskList);
            riskResponse.setRiskLevel(riskList.getRiskLevel());
            riskResponse.setRefTransId(riskList.getRefTransId());
            riskResponse.setRiskParams(riskList.getRiskParams());
            riskResponse.setRiskReason(riskList.getRiskReason());
        }
        return Response.buildSuccess().setData(riskResponse);
    }
}