package com.noahpay.pay.channel.wx.response.tool;

import com.noahpay.pay.channel.wx.response.WxPayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay gethbinfo response
 */
@Getter
@Setter
public class WxPayGetHbInfoResponse extends WxPayResponse {

    private static final long serialVersionUID = 4881040160674770274L;

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
     * 使用API发放现金红包时返回的红包单号
     */
    private String detailId;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户订单号
     */
    private String mchBillno;
    /**
     * 红包状态 <br/>
     * SENDING:发放中 <br/>
     * SENT:已发放待领取 <br/>
     * FAILED：发放失败 <br/>
     * RECEIVED:已领取 <br/>
     * RFUND_ING:退款中 <br/>
     * REFUND:已退款
     */
    private String status;
    /**
     * 发放类型 <br/>
     * API:通过API接口发放 <br/>
     * UPLOAD:通过上传文件方式发放 <br/>
     * ACTIVITY:通过活动方式发放
     */
    private String sendType;
    /**
     * 红包类型 <br/>
     * GROUP:裂变红包 <br/>
     * NORMAL:普通红包
     */
    private String hbType;
    /**
     * 红包个数
     */
    private String totalNum;
    /**
     * 付款金额，单位分
     */
    private String totalAmount;
    /**
     * 发送失败原因
     */
    private String reason;
    /**
     * 红包发送时间
     */
    private String sendTime;
    /**
     * 红包的退款时间（如果其未领取的退款）
     */
    private String refundTime;
    /**
     * 红包退款金额
     */
    private String refundAmount;
    /**
     * 祝福语
     */
    private String wishing;
    /**
     * 活动描述，低版本微信可见
     */
    private String remark;
    /**
     * 发红包的活动名称
     */
    private String actName;
    /**
     * 裂变红包的领取列表
     */
    private String hblist;
}
