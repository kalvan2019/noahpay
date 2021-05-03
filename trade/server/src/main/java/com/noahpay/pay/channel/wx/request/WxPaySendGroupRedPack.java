package com.noahpay.pay.channel.wx.request;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 微信发放裂变红包业务请求参数
 */
@Getter
@Setter
public class WxPaySendGroupRedPack implements Serializable {
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
     * 红包金额设置方式  ALL_RAND—全部随机,商户指定总金额和红包发放总人数，由微信支付随机计算出各红包金额
     */
    private String amt_type = "ALL_RAND";
    /**
     * 红包祝福语
     */
    private String wishing;

    /**
     * 活动名称
     */
    private String act_name;
    /**
     * 备注信息
     */
    private String remark;
    /**
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

    public WxPaySendGroupRedPack() {
        super();
    }

    public WxPaySendGroupRedPack(String mch_billno, String send_name,
                                 String re_openid, String total_amount, String total_num,
                                 String wishing, String act_name, String remark) {
        super();
        this.mch_billno = mch_billno;
        this.send_name = send_name;
        this.re_openid = re_openid;
        this.total_amount = total_amount;
        this.total_num = total_num;
        this.wishing = wishing;
        this.act_name = act_name;
        this.remark = remark;
    }

    public void setAmt_type(String amt_type) {
        if (StringUtils.isEmpty(amt_type) || !"ALL_RAND".equals(amt_type)) {
            amt_type = "ALL_RAND";
        }
        this.amt_type = amt_type;
    }
}
