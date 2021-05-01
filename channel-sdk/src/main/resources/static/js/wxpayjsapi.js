/**
 * 微信内H5调起支付<br>
 * 从com.stary.pay.wxpay.Wxpay.getBrandWCPayRequest()获取参数
 * @param getBrandWCPayRequest 
 */
function onBridgeReady(getBrandWCPayRequest) {
	if (typeof WeixinJSBridge != "undefined") {
		WeixinJSBridge.invoke(
				'getBrandWCPayRequest', 
				getBrandWCPayRequest,
				function(res) {
					if (res.err_msg == "get_brand_wcpay_request:ok"){
						// 使用以上方式判断前端返回,微信团队郑重提示：
						//res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
					} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
						// 支付过程中用户取消
					} else if (res.err_msg == "get_brand_wcpay_request:fail") {
						// 支付失败
					}
				}); 
	}
}
