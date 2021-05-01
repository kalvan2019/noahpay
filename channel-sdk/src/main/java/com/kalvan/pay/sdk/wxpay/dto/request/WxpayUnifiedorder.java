package com.kalvan.pay.sdk.wxpay.dto.request;

import com.kalvan.pay.sdk.wxpay.api.rules.WxpayTradeType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信统一下单业务请求参数
 */
@Getter
@Setter
public class WxpayUnifiedorder extends SubMerchantInfo {

    private static final long serialVersionUID = 2380554180818897542L;

    /**
     * 商品描述
     */
    private String body;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 订单总金额，单位为分
     */
    private String total_fee;
    /**
     * 三位币种代码如 USD
     */
    private String fee_type;
    /**
     * 终端IP
     */
    private String spbill_create_ip;
    /**
     * 交易类型 取值如下：JSAPI，NATIVE，APP等
     */
    private WxpayTradeType trade_type;
    /**
     * 用户标识  trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
     */
    private String openid;
    /**
     * 子公众号用户标识
     */
    private String sub_openid;
    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
     */
    private String attach;
    /**
     * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
     */
    private String device_info = "WEB";
    /**
     * 商品详细描述
     */
    private String detail;
    /**
     * 订单生成时间，格式为yyyyMMddHHmmss
     */
    private String time_start;
    /**
     * 订单失效时间，格式为yyyyMMddHHmmss
     */
    private String time_expire;
    /**
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数
     */
    private String goods_tag;
    /**
     * 上传此参数no_credit--可限制用户不能使用信用卡支付
     */
    private String limit_pay;
    /**
     * 电子发票入口开放标识<br/>
     * Y，传入Y时，支付成功消息和支付详情页将出现开票入口。<br/>
     * 需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效。<br/>
     */
    private String receipt;
    /**
     * trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     */
    private String product_id;
    /**
     * 场景信息<br/>
     * 该字段常用于线下活动时的场景信息上报，支持上报实际门店信息，商户也可以按需求自己上报相关信息。<br/>
     * 该字段为JSON对象数据，对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "所在地行政区划码","address": "详细地址" }}
     */
    private String scene_info;

    public WxpayUnifiedorder() {
        super();
    }

    /**
     * 扫码支付
     *
     * @param body
     * @param out_trade_no
     * @param total_fee
     * @param spbill_create_ip
     */
    public WxpayUnifiedorder(String body, String out_trade_no,
                             String total_fee, String spbill_create_ip) {
        super();
        this.body = body;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.trade_type = WxpayTradeType.NATIVE;
    }

    /**
     * JSAPI支付
     *
     * @param body
     * @param out_trade_no
     * @param total_fee
     * @param spbill_create_ip
     * @param openid
     */
    public WxpayUnifiedorder(String body, String out_trade_no,
                             String total_fee, String spbill_create_ip, String openid) {
        super();
        this.body = body;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.trade_type = WxpayTradeType.JSAPI;
        this.openid = openid;
    }

    public WxpayUnifiedorder(String body, String out_trade_no,
                             String total_fee, String spbill_create_ip, WxpayTradeType trade_type) {
        super();
        this.body = body;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.trade_type = trade_type;
    }
}
