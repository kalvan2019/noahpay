package com.noahpay.pay.channel.wx.response.tool;

import com.noahpay.pay.channel.wx.response.WxPayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询企业付款（转账）记录响应信息
 */
@Getter
@Setter
public class WxPayGetTransferInfoResponse extends WxPayResponse {


    private static final long serialVersionUID = 8620436418389964233L;

    /*------------ 以下字段在return_code为SUCCESS的时候有返回  ------------*/

    /**
     * 业务结果 SUCCESS/FAIL
     */
    private String resultCode;
    /**
     * 错误代码 当result_code为FAIL时返回
     */
    private String errCode;
    /**
     * 错误代码描述 当result_code为FAIL时返回
     */
    private String errCodeDes;

    /*------------ 以下字段在return_code 、result_code都为SUCCESS时有返回 ------------*/

    /**
     * 商户单号
     */
    private String partnerTradeNo;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户号的appid
     */
    private String appid;
    /**
     * 微信付款单号
     */
    private String detailId;
    /**
     * 转账状态 <br>
     * SUCCESS：转账成功 <br>
     * FAILED：转账失败 <br>
     * PROCESSING：处理中 <br>
     */
    private String status;
    /**
     * 收款用户openid
     */
    private String openid;
    /**
     * 企业付款备注
     */
    private String desc;

    /**
     * 付款金额，单位分
     */
    private String paymentAmount;
    /**
     * 付款失败原因
     */
    private String reason;
    /**
     * 转账时间
     */
    private String transferTime;
    /**
     * 付款成功时间
     */
    private String paymentTime;
    /**
     * 收款用户姓名
     */
    private String transferName;

}
