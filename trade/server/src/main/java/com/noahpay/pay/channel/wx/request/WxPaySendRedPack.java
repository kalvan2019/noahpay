package com.noahpay.pay.channel.wx.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信发放普通现金红包业务请求参数
 */
@Getter
@Setter
public class WxPaySendRedPack implements Serializable {
    /**
     * 商户订单号
     */
    private String mch_billno;
    /**
     * 商户名称
     */
    private String send_name;
    /**
     * 用户openid
     */
    private String re_openid;
    /**
     * 付款金额，单位分
     */
    private String total_amount;
    /**
     * 红包发放总人数
     */
    private String total_num;
    /**
     * 红包祝福语
     */
    private String wishing;
    /**
     * 调用接口的机器Ip地址
     */
    private String client_ip;
    /**
     * 活动名称
     */
    private String act_name;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 发放红包使用场景，红包金额大于200或者小于1元时必传<br>
     * PRODUCT_1:商品促销<br>
     * PRODUCT_2:抽奖<br>
     * PRODUCT_3:虚拟物品兑奖<br>
     * PRODUCT_4:企业内部福利<br>
     * PRODUCT_5:渠道分润<br>
     * PRODUCT_6:保险回馈<br>
     * PRODUCT_7:彩票派奖<br>
     * PRODUCT_8:税务刮奖<br>
     */
    private String scene_id;
    /**
     * 活动信息
     */
    private String risk_info;
}
