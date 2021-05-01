package com.kalvan.pay.sdk.wxpay.dto.request;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 微信发放小程序红包业务请求参数

 * @version 1.3.0
 */
@Getter
@Setter
public class WxpaySendMiniProgramHb implements Serializable {

	private static final long serialVersionUID = 7524387142800445300L;

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
	 * 通知用户形式,通过JSAPI方式领取红包,小程序红包固定传MINI_PROGRAM_JSAPI
	 */
	private String notify_way = "MINI_PROGRAM_JSAPI";
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

	public WxpaySendMiniProgramHb() {
		super();
	}
	public WxpaySendMiniProgramHb(String mch_billno, String send_name,
			String re_openid, String total_amount, String total_num,
			String wishing, String client_ip, String act_name, String remark) {
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

	public WxpaySendMiniProgramHb(String mch_billno, String send_name,
			String re_openid, String total_amount, String total_num,
			String wishing, String client_ip, String act_name, String remark,
			String scene_id) {
		super();
		this.mch_billno = mch_billno;
		this.send_name = send_name;
		this.re_openid = re_openid;
		this.total_amount = total_amount;
		this.total_num = total_num;
		this.wishing = wishing;
		this.act_name = act_name;
		this.remark = remark;
		this.scene_id = scene_id;
	}
}