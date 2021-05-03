/*微信分享jsapi：先调用 jsapiconfig.js文件中wx_jsapiConfig()函数*/
var title = "title";
var desc = "desc";
var link = "http://www.xxxxx.com";
var imgUrl = "http://www.xxxxx.com/img/xxxxx.jpg";

wx.ready(function () {   //需在用户可能点击分享按钮前就先调用
	/**
	 * 自定义“分享给朋友”及“分享到QQ”按钮的分享内容（1.4.0）
	 */
	wx.updateAppMessageShareData({ 
		title:title, // 分享标题
		desc: desc, // 分享描述
		link: link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		imgUrl: imgUrl, // 分享图标
	}, function(res) { 
//		这里是回调函数 
	}); 
	/**
	 * 自定义“分享到朋友圈”及“分享到QQ空间”按钮的分享内容（1.4.0）
	 */
	wx.updateTimelineShareData({ 
		title: title, // 分享标题
		link: link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		imgUrl: imgUrl, // 分享图标
	}, function(res) { 
//		这里是回调函数 
	}); 
	/**
	 * 获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口
	 */
	wx.onMenuShareWeibo({
		title: title, // 分享标题
		desc: desc, // 分享描述
		link: link, // 分享链接
		imgUrl: imgUrl, // 分享图标
		success: function () {
			// 用户确认分享后执行的回调函数
		},
		cancel: function () {
			// 用户取消分享后执行的回调函数
		}
	});
	/**
	 * 获取“分享到QQ空间”按钮点击状态及自定义分享内容接口
	 */
	wx.onMenuShareQZone({
		title: title, // 分享标题
		desc: desc, // 分享描述
		link: link, // 分享链接
		imgUrl: imgUrl, // 分享图标
		success: function () {
			// 用户确认分享后执行的回调函数
		},
		cancel: function () {
			// 用户取消分享后执行的回调函数
		}
	});
});

function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}