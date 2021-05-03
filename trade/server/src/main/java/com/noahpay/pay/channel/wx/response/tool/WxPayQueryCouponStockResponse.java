package com.noahpay.pay.channel.wx.response.tool;

import com.noahpay.pay.channel.wx.response.WxPayResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * wxpay query_coupon_stock response
 */
@Getter
@Setter
public class WxPayQueryCouponStockResponse extends WxPayResponse {

    private static final long serialVersionUID = 2278208556296512086L;

    /*------------ 以下字段在return_code为SUCCESS的时候有返回  ------------*/

    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 微信支付分配的终端设备号
     */
    private String deviceInfo;
    /**
     * 签名
     */
    private String sign;
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
    /**
     * 代金券批次id
     */
    private String couponStockId;
    /**
     * 代金券名称
     */
    private String couponName;
    /**
     * 代金券面值,单位是分
     */
    private String couponValue;
    /**
     * 代金券使用最低限额,单位是分
     */
    private String couponMininumn;
    /**
     * 批次状态： 1-未激活；2-审批中；4-已激活；8-已作废；16-中止发放；
     */
    private String couponStockStatus;
    /**
     * 代金券数量
     */
    private String couponTotal;
    /**
     * 代金券每个人最多能领取的数量, 如果为0，则表示没有限制
     */
    private String maxQuota;
    /**
     * 代金券已经发送的数量
     */
    private String isSendNum;
    /**
     * 生效开始时间，格式为时间戳：1943787483
     */
    private String beginTime;
    /**
     * 生效结束时间，格式为时间戳：1943787490
     */
    private String endTime;
    /**
     * 创建时间，格式为时间戳：1943787420
     */
    private String createTime;
    /**
     * 代金券预算额度
     */
    private String couponBudget;
    /**
     * 生效开始时间，格式为年月日时间制：20181126152401
     */
    private String beginTimeT;
    /**
     * 生效结束时间，格式为年月日时间制：20181126152401
     */
    private String endTimeT;
    /**
     * 创建时间，格式为年月日时间制：20181126152401
     */
    private String createTimeT;
}
