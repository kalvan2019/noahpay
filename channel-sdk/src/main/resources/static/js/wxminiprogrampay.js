//微信小程序调起支付
wx.requestPayment({
'timeStamp': '',
'nonceStr': '',
'package': '',
'signType': 'MD5',
'paySign': '',
'success':function(res){
	if (res.err_msg == "requestPayment:ok" ) {
		// 调用支付成功
	} 
},
'fail':function(res){
	if (res.err_msg == "requestPayment:fail cancel") {
		// 用户取消支付
	} else {
		// requestPayment:fail (detail message)
		// 调用支付失败，其中 detail message 为后台返回的详细失败原因
	}
},
'complete':function(res){
	// 调用成功、失败都会执行
}
}) ;