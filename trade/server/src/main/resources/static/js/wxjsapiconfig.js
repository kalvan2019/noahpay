
/*先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”，可以按照提示进行填写，最多填写三个。*/
/*在需要调用JS接口的页面引入如下JS文件，（支持https）：http://res.wx.qq.com/open/js/jweixin-1.4.0.js*/
/**
 * 通过config接口注入权限验证配置<br/>
 * 从com.stary.pay.wxpay.Wxpay.jsApiConfig()获取参数
 * @param jsapiConfig
 */
function wx_jsapiConfig(jsapiConfig) {
	if (typeof wx != "undefined") {
		wx.config(jsapiConfig);
	}
}