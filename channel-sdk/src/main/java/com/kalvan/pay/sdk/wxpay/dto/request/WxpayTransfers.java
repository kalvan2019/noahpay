package com.kalvan.pay.sdk.wxpay.dto.request;

import com.kalvan.pay.sdk.wxpay.api.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信企业付款（转账）业务请求参数
 */
@Getter
@Setter
public class WxpayTransfers implements Serializable {

    private static final long serialVersionUID = 3825748657532263062L;

    /**
     * 微信支付分配的终端设备号
     */
    private String device_info = "WEB";
    /**
     * 商户订单号
     */
    private String partner_trade_no;
    /**
     * 商户appid下，某用户的openid
     */
    private String openid;
    /**
     * 校验用户姓名选项<br>
     * NO_CHECK：不校验真实姓名<br>
     * FORCE_CHECK：强校验真实姓名
     */
    private String check_name = "NO_CHECK";
    /**
     * 收款用户真实姓名<br>
     * 如果check_name设置为FORCE_CHECK，则必填用户真实姓名
     */
    private String re_user_name;
    /**
     * 企业付款金额，单位为分
     */
    private String amount;
    /**
     * 企业付款备注，必填
     */
    private String desc;
    /**
     * IP地址，该IP可传用户端或者服务端的IP
     */
    private String spbill_create_ip;

    public WxpayTransfers() {
        super();
    }

    /**
     * 不校验真实姓名付款（转账）
     *
     * @param partner_trade_no
     * @param openid
     * @param amount
     * @param desc
     * @param spbill_create_ip
     */
    public WxpayTransfers(String partner_trade_no, String openid, String amount, String desc, String spbill_create_ip) {
        super();
        this.partner_trade_no = partner_trade_no;
        this.openid = openid;
        this.check_name = "NO_CHECK";
        this.amount = amount;
        this.desc = desc;
        this.spbill_create_ip = spbill_create_ip;
    }

    /**
     * 强校验真实姓名付款（转账）
     *
     * @param partner_trade_no
     * @param openid
     * @param re_user_name
     * @param amount
     * @param desc
     * @param spbill_create_ip
     */
    public WxpayTransfers(String partner_trade_no, String openid, String re_user_name,
                          String amount, String desc, String spbill_create_ip) {
        super();
        this.partner_trade_no = partner_trade_no;
        this.openid = openid;
        this.check_name = "FORCE_CHECK";
        this.re_user_name = re_user_name;
        this.amount = amount;
        this.desc = desc;
        this.spbill_create_ip = spbill_create_ip;
    }

    public void setCheck_name(String check_name) {
        if (StringUtils.isEmpty(check_name)
                || (!"NO_CHECK".equals(check_name) && !"FORCE_CHECK".equals(check_name))) {
            check_name = "NO_CHECK";
        }
        this.check_name = check_name;
    }
}
