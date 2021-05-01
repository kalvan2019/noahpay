package com.noahpay.pay.channel.bean.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenliang
 */
@Setter
@Getter
public class ChannelMerRegisterRequest extends ChannelBaseRequest implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Long merchantNo;
    private String merchantName;
    private String merchantType;
    private String businessLicenseCode;
    private String businessLicenseName;
    private String businessLicensePhoto;
    private String businessLicenseExpired;
    private String mcc;
    private String businessAddress;
    private String province;
    private String city;
    private String area;
    private String storeHeadPhoto;
    private String storeShopPhoto;
    private String storeHallPhoto;
    private String storeCashierPhoto;
    private String lawyerName;
    private String lawyerCertType;
    private String lawyerCertNo;
    private String lawyerCertPhotoFront;
    private String lawyerCertPhotoBack;
    private String contactPerson;
    private String contactPhone;
    private String serviceTel;
    private String email;
    private String settleAccountType;
    private String settleAccountNo;
    private String settleAccount;
    private String settlePeriod;
    private String openingLicenseAccountPhoto;
    private String acquiringAgreementPhoto;
    private String settleAuthLetterPhoto;
    private String bankCardPhotoFront;
    private String bankCardPhotoBack;
    private String openBank;
    private String openSubBank;
    private String openBankCode;
    private String openBankReservePhone;
    private String authorizedPersonCertNo;
    private String authorizedPersonCertType;
    private String authorizedCertPhotoFront;
    private String authorizedCertPhotoBack;
    private String reserve1;
    /**
     * 小额交易金额小于（含）1000 元的银联手续费，借记卡，单位：万分
     */
    private Long smallFeeRateUnionPayDebit;
    /**
     * 小额交易金额小于（含）1000 元的银联手续费率(借记封顶)，单位：分
     */
    private Long smallFeeRateUnionPayDebitCap;
    /**
     * 小额交易金额小于（含）1000 元的银联手续费率(贷记)
     */
    private Long smallFeeRateUnionPayCredit;
    /**
     * 交易金额大于1000 元的银联手续费，借记卡，单位：万分
     */
    private Long largeFeeRateUnionPayDebit;
    /**
     * 额交易金额大于1000 元的银联手续费率(借记封顶)，单位：分
     */
    private Long largeFeeRateUnionPayDebitCap;
    /**
     * 额交易金额大于1000 元的银联手续费率(贷记)，单位：万分
     */
    private Long largeFeeRateUnionPayCredit;
    /**
     * 通道收单机构父商户号-用于收单资金结算
     */
    private String channelDfMerchantNo;
}
